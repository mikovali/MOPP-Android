package ee.ria.DigiDoc.android.signature.update;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

import ee.ria.DigiDoc.R;
import ee.ria.DigiDoc.android.utils.rxbinding.app.ObservableDialogClickListener;
import io.reactivex.Observable;

import static ee.ria.DigiDoc.android.Constants.VOID;

public final class SignatureUpdateSignatureAddDialog extends AlertDialog {

    private final SignatureUpdateSignatureAddView view;
    private final ObservableDialogClickListener positiveButtonClicks;

    public SignatureUpdateSignatureAddDialog(@NonNull Context context) {
        super(context);
        TypedArray a = context.obtainStyledAttributes(new int[]{R.attr.dialogPreferredPadding});
        int padding = a.getDimensionPixelSize(0, 0);
        a.recycle();

        view = new SignatureUpdateSignatureAddView(getContext());
        view.setId(R.id.signatureUpdateSignatureAdd);
        setView(view, padding, padding, padding, padding);
        setButton(BUTTON_POSITIVE, getContext().getString(android.R.string.ok),
                positiveButtonClicks = new ObservableDialogClickListener());
        setButton(BUTTON_NEGATIVE, getContext().getString(android.R.string.cancel),
                (dialog, which) -> cancel());
    }

    public SignatureUpdateSignatureAddView view() {
        return view;
    }

    public Observable<Object> positiveButtonClicks() {
        return positiveButtonClicks.map(ignored -> VOID);
    }
}