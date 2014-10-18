package net.nebur.basewebapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

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
        // TODO Move warmup out of the UIThread
        warmupLocalStorage();
        setupWebView();
        webview.loadUrl("file:///" + getFilesDir() + "/index.html");
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void warmupLocalStorage() {
        // TODO Remove format call
        //storageManager.format();
        if (!storageManager.isValid()) {
            storageManager.reset();
        }
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
    }
}
