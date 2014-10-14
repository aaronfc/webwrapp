package net.nebur.basewebapp.storage;

import java.io.File;

/**
 * WebAppStorageManager interface
 */
public interface WebAppStorageManager {
    public boolean isValid();
    public void reset();
    public void format();
    public File getPath();
}
