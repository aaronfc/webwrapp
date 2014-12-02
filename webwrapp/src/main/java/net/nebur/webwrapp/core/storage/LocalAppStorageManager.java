package net.nebur.webwrapp.core.storage;

/**
 * LocalAppStorageManager interface
 */
public interface LocalAppStorageManager {
    /**
     * Empty current local app storage.
     */
    public void format();

    /**
     * Get root path to local app storage.
     * @return Root path to local app storage as a string.
     */
    public String getStoragePath();

    /**
     * Get full path to the index file.
     * @return Full path of the index file.
     */
    public String getIndexFullPath();

    /**
     * Check if storage is updated to last version.
     * @return Whether last version matches the local one.
     */
    public boolean isUpdated();

    /**
     * Update local storage contents with the last version from remote.
     */
    public void update();
}
