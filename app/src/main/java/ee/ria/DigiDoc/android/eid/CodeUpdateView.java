package ee.ria.DigiDoc.android.eid;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding2.support.v7.widget.RxToolbar;

import ee.ria.DigiDoc.R;
import io.reactivex.Observable;

public final class CodeUpdateView extends CoordinatorLayout {

    private final Toolbar toolbarView;
    private final TextView textView;
    private final TextInputLayout currentLabelView;
    private final EditText currentView;
    private final TextInputLayout newLabelView;
    private final EditText newView;
    private final TextInputLayout repeatLabelView;
    private final EditText repeatView;
    private final Button negativeButton;
    private final Button positiveButton;

    public CodeUpdateView(Context context) {
        this(context, null);
    }

    public CodeUpdateView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CodeUpdateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.eid_home_code_update, this);
        toolbarView = findViewById(R.id.toolbar);
        textView = findViewById(R.id.eidHomeCodeUpdateText);
        currentLabelView = findViewById(R.id.eidHomeCodeUpdateCurrentLabel);
        currentView = findViewById(R.id.eidHomeCodeUpdateCurrent);
        newLabelView = findViewById(R.id.eidHomeCodeUpdateNewLabel);
        newView = findViewById(R.id.eidHomeCodeUpdateNew);
        repeatLabelView = findViewById(R.id.eidHomeCodeUpdateRepeatLabel);
        repeatView = findViewById(R.id.eidHomeCodeUpdateRepeat);
        negativeButton = findViewById(R.id.eidHomeCodeUpdateNegativeButton);
        positiveButton = findViewById(R.id.eidHomeCodeUpdatePositiveButton);
    }

    public void action(CodeUpdateAction action) {
        toolbarView.setTitle(action.titleRes());
        textView.setText(action.textRes());
        currentLabelView.setHint(getResources().getString(action.currentRes()));
        newLabelView.setHint(getResources().getString(action.newRes()));
        repeatLabelView.setHint(getResources().getString(action.repeatRes()));
        positiveButton.setText(action.positiveButtonRes());
    }

    public Observable<Object> upClicks() {
        return RxToolbar.navigationClicks(toolbarView);
    }
}