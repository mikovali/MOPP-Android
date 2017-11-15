package ee.ria.DigiDoc.android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.view.View;

import ee.ria.DigiDoc.R;

@VisibleForTesting
public final class ViewTestActivity extends Activity {

    private static final String TYPE = "type";

    public static Intent createIntent(Class<? extends View> type) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.putExtra(TYPE, type.getName());
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String typeName = getIntent().getStringExtra(TYPE);
        try {
            //noinspection unchecked
            Class<? extends View> type = (Class<? extends View>) Class.forName(typeName);
            View view = type.getConstructor(Context.class).newInstance(this);
            view.setId(R.id.viewUnderTest);
            setContentView(view);
        } catch (Exception e) {
            throw new IllegalArgumentException("Can't create view class " + typeName);
        }
    }
}
