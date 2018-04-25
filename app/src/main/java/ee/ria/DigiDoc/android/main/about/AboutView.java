package ee.ria.DigiDoc.android.main.about;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.jakewharton.rxbinding2.support.v7.widget.RxToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import ee.ria.DigiDoc.BuildConfig;
import ee.ria.DigiDoc.R;
import ee.ria.DigiDoc.android.Application;
import ee.ria.DigiDoc.android.utils.ViewDisposables;
import ee.ria.DigiDoc.android.utils.navigator.Navigator;
import ee.ria.DigiDoc.android.utils.navigator.Transaction;

public class AboutView extends ScrollView {

    private final Toolbar toolbarView;

    private final Navigator navigator;

    private final ViewDisposables disposables;

    @BindView(R.id.mainAboutRiaDigiDocVersionTitle) TextView riaDigiDocVersionTitle;
    @BindView(R.id.mainAboutPreferenceV7FixVersionTitle) TextView preferenceV7FixVersionTitle;
    @BindView(R.id.mainAboutGuavaVersionTitle) TextView guavaVersionTitle;
    @BindView(R.id.mainAboutCommonsIoVersionTitle) TextView commonsIoVersionTitle;
    @BindView(R.id.mainAboutOkioVersionTitle) TextView okioVersionTitle;
    @BindView(R.id.mainAboutTimberVersionTitle) TextView timberVersionTitle;
    @BindView(R.id.mainAboutButterknifeVersionTitle) TextView butterknifeVersionTitle;
    @BindView(R.id.mainAboutOkHttpVersionTitle) TextView okHttpVersionTitle;
    @BindView(R.id.mainAboutRetrofitVersionTitle) TextView retrofitVersionTitle;
    @BindView(R.id.mainAboutSpongyCastleVersionTitle) TextView spongyCastleVersionTitle;
    @BindView(R.id.mainAboutMaterialValuesVersionTitle) TextView materialValuesVersionTitle;
    @BindView(R.id.mainAboutDaggerVersionTitle) TextView daggerVersionTitle;
    @BindView(R.id.mainAboutConductorVersionTitle) TextView conductorVersionTitle;
    @BindView(R.id.mainAboutRxJavaVersionTitle) TextView rxJavaVersionTitle;
    @BindView(R.id.mainAboutRxAndroidVersionTitle) TextView rxAndroidVersionTitle;
    @BindView(R.id.mainAboutRxBindingVersionTitle) TextView rxBindingVersionTitle;
    @BindView(R.id.mainAboutAutoValueVersionTitle) TextView autoValueVersionTitle;
    @BindView(R.id.mainAboutAutoValueParcelVersionTitle) TextView autoValueParcelVersionTitle;
    @BindView(R.id.mainAboutThreeTenAbpVersionTitle) TextView threeTenAbpVersionTitle;
    @BindView(R.id.mainAboutExpandableLayoutVersionTitle) TextView expandableLayoutVersionTitle;
    @BindView(R.id.mainAboutJunitVersionTitle) TextView junitVersionTitle;
    @BindView(R.id.mainAboutTruthVersionTitle) TextView truthVersionTitle;

    public AboutView(Context context) {
        super(context);
        inflate(context, R.layout.about_screen, this);
        toolbarView = findViewById(R.id.toolbar);
        navigator = Application.component(context).navigator();
        disposables = new ViewDisposables();
        ButterKnife.bind(this);

        appendTitleVersions();
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        disposables.attach();
        disposables.add(RxToolbar.navigationClicks(toolbarView).subscribe(o ->
                navigator.execute(Transaction.pop())));
    }

    @Override
    public void onDetachedFromWindow() {
        disposables.detach();
        super.onDetachedFromWindow();
    }

    private void appendTitleVersions() {
        riaDigiDocVersionTitle.append(" Version " + BuildConfig.VERSION_NAME);
        preferenceV7FixVersionTitle.append(" " + BuildConfig.PREFERENCE_V7_FIX_VERSION);
        guavaVersionTitle.append(" " + BuildConfig.GUAVA_VERSION);
        commonsIoVersionTitle.append(" " + BuildConfig.COMMONS_IO_VERSION);
        okioVersionTitle.append(" " + BuildConfig.OKIO_VERSION);
        timberVersionTitle.append(" " + BuildConfig.TIMBER_VERSION);
        butterknifeVersionTitle.append(" " + BuildConfig.BUTTERKNIFE_VERSION);
        okHttpVersionTitle.append(" " + BuildConfig.OK_HTTP_VERSION);
        retrofitVersionTitle.append(" " + BuildConfig.RETROFIT_VERSION);
        spongyCastleVersionTitle.append(" " + BuildConfig.SPONGY_CASTLE_VERSION);
        materialValuesVersionTitle.append(" " + BuildConfig.MATERIAL_VALUES_VERSION);
        daggerVersionTitle.append(" " + BuildConfig.DAGGER_VERSION);
        conductorVersionTitle.append(" " + BuildConfig.CONDUCTOR_VERSION);
        rxJavaVersionTitle.append(" " + BuildConfig.RX_JAVA_VERSION);
        rxAndroidVersionTitle.append(" " + BuildConfig.RX_ANDROID_VERSION);
        rxBindingVersionTitle.append(" " + BuildConfig.RX_BINDER_VERSION);
        autoValueVersionTitle.append(" " + BuildConfig.AUTO_VALUE_VERSION);
        autoValueParcelVersionTitle.append(" " + BuildConfig.AUTO_VALUE_PARCEL_VERSION);
        threeTenAbpVersionTitle.append(" " + BuildConfig.THREE_TEN_ABP_VERSION);
        expandableLayoutVersionTitle.append(" " + BuildConfig.EXPANDABLE_LAYOUT_VERSION);
        junitVersionTitle.append(" " + BuildConfig.JUNIT_VERSION);
        truthVersionTitle.append(" " + BuildConfig.TRUTH_VERSION);
    }
}
