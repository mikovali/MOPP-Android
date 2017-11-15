package ee.ria.DigiDoc.android;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.view.View;

public final class ViewTestRule extends ActivityTestRule<ViewTestActivity> {

    private final Class<? extends View> type;

    public ViewTestRule(Class<? extends View> type) {
        super(ViewTestActivity.class);
        this.type = type;
    }

    @Override
    protected Intent getActivityIntent() {
        return ViewTestActivity.createIntent(type);
    }
}
