package ee.ria.DigiDoc.android.eid;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;

import ee.ria.DigiDoc.R;

public final class EIDHomeView extends CoordinatorLayout {

    public EIDHomeView(Context context) {
        this(context, null);
    }

    public EIDHomeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EIDHomeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.eid_home, this);
    }
}