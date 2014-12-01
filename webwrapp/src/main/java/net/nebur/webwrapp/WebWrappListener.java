package net.nebur.webwrapp;

/**
 * WebWrapp listener interface.
 * This defines the events you can expect from the WebWrapp library.
 */
public interface WebWrappListener {

    /**
     * Warm up process has been started.
     */
    void onWarmUpStart();

    /**
     * Checking of remote version is ongoing.
     */
    void onCheckingVersion();

    /**
     * Update is available and an update process has been started.
     */
    void onUpdating();

    /**
     * An unexpected event happened.
     * @param ordinal Event ID
     */
    void onUnknownEvent(int ordinal);

    /**
     * Warm up completed, successful or not, and here you have the URL.
     * @param url URL to be loaded by WebView
     */
    void onReady(String url);
}
