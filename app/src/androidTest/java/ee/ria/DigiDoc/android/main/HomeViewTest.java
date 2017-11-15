package ee.ria.DigiDoc.android.main;

import android.support.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;

import ee.ria.DigiDoc.R;
import ee.ria.DigiDoc.android.ViewTestRule;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@LargeTest
public final class HomeViewTest {

    @Rule public final ViewTestRule viewTestRule = new ViewTestRule(HomeView.class);

    @Test
    public void onAttach_defaultScreenShown() {
        onView(withId(R.id.signatureHomeScreen))
                .check(matches(isDisplayed()));
    }

    @Test
    public void cryptoButtonClick_cryptoScreenShown() {
        onView(withId(R.id.mainHomeNavigationCrypto))
                .perform(click());
        onView(withId(R.id.cryptoHomeScreen))
                .check(matches(isDisplayed()));
    }

    @Test
    public void eidButtonClick_eidScreenShown() {
        onView(withId(R.id.mainHomeNavigationEID))
                .perform(click());
        onView(withId(R.id.eidHomeScreen))
                .check(matches(isDisplayed()));
    }

    @Test
    public void eidButtonClick_eidScreenShown_signatureButtonClick_signatureScreenShown() {
        onView(withId(R.id.mainHomeNavigationEID))
                .perform(click());
        onView(withId(R.id.eidHomeScreen))
                .check(matches(isDisplayed()));

        onView(withId(R.id.mainHomeNavigationSignature))
                .perform(click());
        onView(withId(R.id.signatureHomeScreen))
                .check(matches(isDisplayed()));
    }
}
