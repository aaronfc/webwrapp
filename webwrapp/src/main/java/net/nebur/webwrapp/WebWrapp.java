package net.nebur.webwrapp;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import net.nebur.webwrapp.di.WebWrappModule;
import net.nebur.webwrapp.core.tasks.LocalStorageUpdaterTask;
import net.nebur.webwrapp.core.tasks.LocalStorageUpdaterTaskFactory;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import dagger.ObjectGraph;

/**
 * WebWrapp main entry-point
 */
public class WebWrapp {

    private ObjectGraph objectGraph;

    @Inject
    LocalStorageUpdaterTaskFactory updaterTaskFactory;

    private Context context;

    public WebWrapp(Context context) {
        this.context = context;
        startInjection();
    }

    private void startInjection() {
        objectGraph = ObjectGraph.create(getModules().toArray());
        objectGraph.inject(this);
    }

    private List<Object> getModules() {
        return Arrays.<Object>asList(new WebWrappModule(this));
    }

    /**
     * Initialize WebWrapp custom process.
     * This will try to update the local storage while notifying the listener about possible events.
     *
     * @param listener Lister to be notified
     */
    public void warmUp(WebWrappListener listener) {
        LocalStorageUpdaterTask task = updaterTaskFactory.create(listener);
        task.execute();
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void setupWebView(Activity activity, WebView webview) {
        WebViewClient webViewClient = new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        };
        webview.setWebViewClient(webViewClient);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setDomStorageEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            fixJavascriptAccess(webview);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void fixJavascriptAccess(WebView webview) {
        webview.getSettings().setAllowFileAccessFromFileURLs(true);
        webview.getSettings().setAllowUniversalAccessFromFileURLs(true);
    }

    public Context getContext() {
        return context;
    }
}
