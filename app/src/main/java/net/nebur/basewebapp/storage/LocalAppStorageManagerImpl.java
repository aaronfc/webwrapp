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
        File file = new File (getPath().toString() + "/original/index.html");
        return file.exists();
    }

    @Override
    public void reset() {
        // Remove all from internal path
        format();

        // Copy data
        copyFromAssets();
    }

    private void copyFromAssets() {
        String[] files = getFilesFromAssets();
        Log.d("debug", "Files: " + files.length);
        if (files != null) {
            for (String file : files) {
                Log.d("tag", "File from assets: " + file);
                copyFileOrDir(file);
            }
        }
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

    public String[] getFilesFromAssets() {
        AssetManager assetManager = context.getAssets();
        String[] files = null;
        try {
            files = assetManager.list("original");
        } catch (IOException e) {
            Log.e("tag", "Failed to get asset file list.", e);
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
                for (int i = 0; i < assets.length; ++i) {
                    copyFileOrDir(path + "/" + assets[i]);
                }
            }
        } catch (IOException ex) {
            Log.e("tag", "I/O Exception", ex);
        }
    }

    private void copyFile(String filename) {
        AssetManager assetManager = context.getAssets();

        InputStream in = null;
        OutputStream out = null;
        try {
            in = assetManager.open(filename);
            String newFileName = getPath() + "/" + filename;
            Log.d("tag", "File: " + newFileName);
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
            Log.e("tag", e.getMessage());
        }
    }
}
