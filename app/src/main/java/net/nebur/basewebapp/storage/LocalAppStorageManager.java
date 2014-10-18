package net.nebur.basewebapp.storage;

import java.io.File;

/**
 * LocalAppStorageManager interface
 */
public interface LocalAppStorageManager {
    public boolean isValid();
    public void reset();
    public void format();
    public File getPath();
}
