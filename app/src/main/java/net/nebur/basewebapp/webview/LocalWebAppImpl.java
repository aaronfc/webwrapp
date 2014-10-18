package net.nebur.basewebapp.webview;

import android.util.Log;
import android.webkit.WebView;

import net.nebur.basewebapp.storage.LocalAppStorageManager;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * LocalWebApp interface implementation.
 */
public class LocalWebAppImpl implements LocalWebApp {

    private WebView webView;
    private LocalAppStorageManager storage;

    public LocalWebAppImpl(WebView webView, LocalAppStorageManager storage) {
        this.webView = webView;
        this.storage = storage;
    }

    @Override
    public void loadHome() {
        loadLocal("/");
    }

    @Override
    public void loadLocal(String path) {
        try {
            URL localUrl = getLocalUrl(path);
            loadUrl(localUrl);
        } catch (MalformedURLException e) {
            Log.e("ERROR", "MalformedURL for path: " + path);
        }
    }

    @Override
    public void loadUrl(URL destination) {
        webView.loadUrl(destination.toString());
    }

    @Override
    public void refresh() {
        webView.reload();
    }

    private URL getLocalUrl(String path) throws MalformedURLException {
        return new URL(storage.getPath() + path);
    }
}
