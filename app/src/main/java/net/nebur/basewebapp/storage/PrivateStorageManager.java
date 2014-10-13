package net.nebur.basewebapp.storage;

import java.io.File;

/**
 * PrivateStorageManager interface
 */
public interface PrivateStorageManager {
    public boolean isValid();
    public void reset();
    public void format();
    public File getPath();
}
