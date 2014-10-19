package net.nebur.basewebapp.storage;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.res.AssetManager;
import android.util.Log;

import net.nebur.basewebapp.utils.FileUtils;

/**
 * LocalAppStorageManager implementation
 */
public class LocalAppStorageManagerImpl implements LocalAppStorageManager {

    private Context context;

    public LocalAppStorageManagerImpl(Context context) {
        this.context = context;
    }

    @Override
    public boolean isValid() {
        // First approach to validate is to check if index.html file exists
        File file = new File (getPath().toString() + "/webapp/index.html");
        return file.exists();
    }

    @Override
    public void reset() {
        Log.i("INFO", "Resetting local storage to assets");
        // Remove all from internal path
        Log.d("DEBUG", "Formatting");
        format();

        // Copy data
        Log.d("DEBUG", "Copying from assets");
        copyFromAssets();
    }

    private void copyFromAssets() {
        copyFileOrDir("webapp");
    }

    @Override
    public void format() {
        File internalPath = getPath();
        FileUtils.remove(internalPath);
    }

    @Override
    public File getPath() {
        return context.getFilesDir();
    }

    @Override
    public boolean isUpdated() {
        return true;
    }

    @Override
    public void updateFromRemote() {
        // TODO Implement this
    }

    public String[] getFilesFromAssets(String directory) {
        AssetManager assetManager = context.getAssets();
        String[] files = null;
        try {
            files = assetManager.list(directory);
            for(int i = 0; i<files.length; i++) {
                files[i] = directory + "/" + files[i];
            }
        } catch (IOException e) {
            Log.e("ERROR", "Failed to get asset file list.", e);
        }

        return files;
    }

    private void copyFileOrDir(String path) {
        AssetManager assetManager = context.getAssets();
        String assets[] = null;
        try {
            assets = assetManager.list(path);
            if (assets.length == 0) {
                copyFile(path);
            } else {
                String fullPath = getPath() + "/" + path;
                Log.d("DEBUG", "Directory full path: " + fullPath);
                File dir = new File(fullPath);
                if (!dir.exists())
                    dir.mkdir();
                for (String asset : assets) {
                    copyFileOrDir(path + "/" + asset);
                }
            }
        } catch (IOException ex) {
            Log.e("ERROR", "I/O Exception", ex);
        }
    }

    private void copyFile(String filename) {
        AssetManager assetManager = context.getAssets();

        InputStream in = null;
        OutputStream out = null;
        try {
            in = assetManager.open(filename);
            String newFileName = getPath() + "/" + filename;
            Log.d("DEBUG", "File: " + newFileName);
            out = new FileOutputStream(newFileName);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            out.flush();
            out.close();
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
        }
    }
}
