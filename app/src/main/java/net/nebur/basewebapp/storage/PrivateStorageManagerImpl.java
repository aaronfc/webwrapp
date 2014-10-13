package net.nebur.basewebapp.storage;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.res.AssetManager;
import android.util.Log;

/**
 * PrivateStorageManager implementation
 */
public class PrivateStorageManagerImpl implements PrivateStorageManager {

    private Context context;

    public PrivateStorageManagerImpl(Context context) {
        this.context = context;
    }

    @Override
    public boolean isValid() {
        // First approach to validate is to check if index.html file exists
        File file = new File( getPath().toString() + "/index.html");
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
        recursiveDelete(internalPath);
    }

    @Override
    public File getPath() {
        return context.getFilesDir();
    }

    private void recursiveDelete(File target) {
        if (target.isDirectory()) {
            for (File file : target.listFiles()) {
                recursiveDelete(file);
            }
        } else {
            boolean result = target.delete();
            Log.d("DEBUG", "Deleting " + target + ": " + (result ? "YES" : "NO"));
        }
    }

    public String[] getFilesFromAssets() {
        AssetManager assetManager = context.getAssets();
        String[] files = null;
        try {
            files = assetManager.list("");
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
            in = null;
            out.flush();
            out.close();
            out = null;
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }
}
