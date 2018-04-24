package ee.ria.DigiDoc.android.main.diagnostics;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.Toolbar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.jakewharton.rxbinding2.support.v7.widget.RxToolbar;

import ee.ria.DigiDoc.BuildConfig;
import ee.ria.DigiDoc.R;
import ee.ria.DigiDoc.android.Application;
import ee.ria.DigiDoc.android.utils.ViewDisposables;
import ee.ria.DigiDoc.android.utils.navigator.Navigator;
import ee.ria.DigiDoc.android.utils.navigator.Transaction;
import ee.ria.libdigidocpp.digidoc;

public class DiagnosticsView extends ScrollView {

    private final Toolbar toolbarView;

    private final Navigator navigator;

    private final ViewDisposables disposables;

    public DiagnosticsView(Context context) {
        super(context);
        inflate(context, R.layout.diagnostics_screen, this);
        toolbarView = findViewById(R.id.toolbar);
        navigator = Application.component(context).navigator();
        disposables = new ViewDisposables();

        TextView applicationVersion = findViewById(R.id.eidAboutApplicationVersion);
        TextView androidVersion = findViewById(R.id.eidAboutAndroidVersion);
        TextView libDocVersion = findViewById(R.id.eidAboutLibDocVersion);

        applicationVersion.setText(getAppVersion());
        androidVersion.setText(getAndroidVersion());
        libDocVersion.setText(getLibDigiDocVersion());
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

    private static String getAppVersion(){
        return BuildConfig.VERSION_NAME;
    }

    private static String getAndroidVersion(){
        return "Android " + Build.VERSION.RELEASE;
    }

    private static String getLibDigiDocVersion(){
        return digidoc.appInfo() + " " + digidoc.version();
    }
}
