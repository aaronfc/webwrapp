package net.nebur.basewebapp.activities;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import net.nebur.basewebapp.R;
import net.nebur.basewebapp.storage.LocalAppStorageManager;
import net.nebur.basewebapp.storage.LocalAppStorageManagerImpl;


/**
 * Main activity with fullscreen webview to be used for a webapp.
 */
public class MainActivity extends Activity {

    /**
     * Private internal webview reference
     */
    private WebView webview;

    /**
     * Private storage manager
     */
    private LocalAppStorageManager storageManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        storageManager = new LocalAppStorageManagerImpl(this);
        setupWebView();
        webview.loadUrl("file:///" + getFilesDir() + "/webapp/index.html");
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
