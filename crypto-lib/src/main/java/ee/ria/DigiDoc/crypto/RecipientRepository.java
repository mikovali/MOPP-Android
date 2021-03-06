package ee.ria.DigiDoc.crypto;

import android.support.annotation.WorkerThread;

import com.google.common.collect.ImmutableList;
import com.unboundid.asn1.ASN1OctetString;
import com.unboundid.ldap.sdk.Attribute;
import com.unboundid.ldap.sdk.LDAPConnection;
import com.unboundid.ldap.sdk.LDAPSearchException;
import com.unboundid.ldap.sdk.SearchResult;
import com.unboundid.ldap.sdk.SearchResultEntry;
import com.unboundid.util.ssl.SSLUtil;
import com.unboundid.util.ssl.TrustAllTrustManager;

import org.bouncycastle.asn1.x509.KeyPurposeId;
import org.bouncycastle.asn1.x509.KeyUsage;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.net.ssl.SSLSocketFactory;

import ee.ria.DigiDoc.common.Certificate;
import ee.ria.DigiDoc.common.EIDType;
import okio.ByteString;

import static com.unboundid.ldap.sdk.SearchScope.SUB;

/**
 * Repository for encryption recipients from SK LDAP and ESTEID SK LDAP server.
 */
public final class RecipientRepository {

    private static final String EST_EID_LDAP_HOST = "esteid.ldap.sk.ee";
    private static final int EST_EID_LDAP_PORT = 636;
    private static final String LDAP_SK_EE_HOST = "ldap.sk.ee";
    private static final int LDAP_SK_EE_PORT = 389;
    private static final String CERT_BINARY_ATTR = "userCertificate;binary";
    private static final String BASE_DN = "c=EE";

    /**
     * Tries to find certificates first from ESTEID SK LDAP server. If that fails or no certificates
     * are found then tries to find certificates from SK LDAP Server
     * <p>
     * If query is numeric then searches by personal code, otherwise by CN fields.
     *
     * @param query Query to executeSearch for.
     * @return List of matched certificates.
     * @throws CryptoException When something went wrong
     */
    @WorkerThread
    public final ImmutableList<Certificate> find(String query) throws CryptoException {
        try {
            ImmutableList<Certificate> certs = findFromEsteidLdap(query);
            return certs.isEmpty() ? findFromLdap(query) : certs;
        } catch (CryptoException e) {
            return findFromLdap(query);
        }
    }

    private ImmutableList<Certificate> findFromEsteidLdap(String query) throws CryptoException {
        try (LDAPConnection connection = new LDAPConnection(getSslSocketFactory())) {
            connection.connect(EST_EID_LDAP_HOST, EST_EID_LDAP_PORT);
            return executeSearch(connection, new EstEidLdapFilter(query));
        } catch (Exception e) {
            throw new CryptoException("Finding recipients failed", e);
        }
    }

    private ImmutableList<Certificate> findFromLdap(String query) throws CryptoException {
        try (LDAPConnection connection = new LDAPConnection(LDAP_SK_EE_HOST, LDAP_SK_EE_PORT)) {
            return executeSearch(connection, new LdapFilter(query));
        } catch (Exception e) {
            throw new CryptoException("Finding recipients failed", e);
        }
    }

    private ImmutableList<Certificate> executeSearch(LDAPConnection connection, LdapFilter ldapFilter)
            throws LDAPSearchException, IOException
    {
        ImmutableList.Builder<Certificate> builder = ImmutableList.builder();
        SearchResult result = connection.search(BASE_DN, SUB, ldapFilter.filterString(), CERT_BINARY_ATTR);
        for (SearchResultEntry entry : result.getSearchEntries()) {
            for (Attribute attribute : entry.getAttributes()) {
                for (ASN1OctetString value : attribute.getRawValues()) {
                    Certificate certificate = Certificate.create(ByteString.of(value.getValue()));
                    if (isSuitableKeyAndNotMobileId(certificate)) {
                        builder.add(certificate);
                    }
                }
            }
        }
        return builder.build();
    }

    private SSLSocketFactory getSslSocketFactory() throws GeneralSecurityException {
        SSLUtil sslUtil = new SSLUtil(new TrustAllTrustManager());
        return sslUtil.createSSLSocketFactory();
    }

    private boolean isSuitableKeyAndNotMobileId(Certificate certificate) {
        return (hasKeyEnciphermentUsage(certificate) || hasKeyAgreementUsage(certificate)) &&
                !isServerAuthKeyPurpose(certificate) &&
                (!isESealType(certificate) || !isTlsClientAuthKeyPurpose(certificate)) &&
                !isMobileIdType(certificate);
    }

    private boolean isTlsClientAuthKeyPurpose(Certificate certificate) {
        return certificate.extendedKeyUsage().hasKeyPurposeId(KeyPurposeId.id_kp_clientAuth);
    }

    private boolean hasKeyAgreementUsage(Certificate certificate) {
        return certificate.keyUsage().hasUsages(KeyUsage.keyAgreement);
    }

    private boolean hasKeyEnciphermentUsage(Certificate certificate) {
        return certificate.keyUsage().hasUsages(KeyUsage.keyEncipherment);
    }

    private boolean isServerAuthKeyPurpose(Certificate certificate) {
        return certificate.extendedKeyUsage().hasKeyPurposeId(KeyPurposeId.id_kp_serverAuth);
    }

    private boolean isMobileIdType(Certificate certificate) {
        return certificate.type().equals(EIDType.MOBILE_ID);
    }

    private boolean isESealType(Certificate certificate) {
        return certificate.type().equals(EIDType.E_SEAL);
    }
}
