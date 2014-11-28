package net.nebur.basewebapp.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import net.nebur.basewebapp.R;
import net.nebur.basewebapp.di.MainModule;
import net.nebur.basewebapp.storage.LocalAppStorageManager;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;


/**
 * Main activity with fullscreen webview to be used for a webapp.
 */
public class MainActivity extends BaseActivity {

    /**
     * Private internal webview reference
     */
    private WebView webview;

    @Inject
    LocalAppStorageManager storageManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        setupWebView();
        webview.loadUrl("file:///" + storageManager.getPath() + "/index.html");
    }

    @Override
    protected List<Object> getModules() {
        return Arrays.<Object>asList(new MainModule(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setupWebView() {
        webview = (WebView) findViewById(R.id.webView);
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
}
