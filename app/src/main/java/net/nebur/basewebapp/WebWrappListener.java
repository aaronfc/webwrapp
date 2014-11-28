package net.nebur.basewebapp;

/**
 * WebWrapp listener interface
 */
public interface WebWrappListener {
    public void onReady(String url);

    void onStart();

    void onCheckingVersion();

    void onUpdateAvailable();

    void onUnknownEvent(int ordinal);
}
