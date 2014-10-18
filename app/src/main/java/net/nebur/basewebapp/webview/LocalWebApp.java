package net.nebur.basewebapp.webview;

import java.net.URL;

/**
 * LocalWebApp interface.
 */
public interface LocalWebApp {
    /**
     * Load main view.
     */
    public void loadHome();

    /**
     * Load local resource given its path.
     * @param path Path to resource in local storage
     */
    public void loadLocal(String path);

    /**
     * This shouldn't be needed.
     * @param destination URL to load
     */
    public void loadUrl(URL destination);

    /**
     * Refresh current view.
     */
    public void refresh();
}
