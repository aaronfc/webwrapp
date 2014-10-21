package net.nebur.basewebapp.storage;

import android.content.Context;
import android.util.Log;

import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * LocalAppStorageManager implementation
 */
public class LocalAppStorageManagerImpl implements LocalAppStorageManager {

    // TODO Move this to config
    private final static String DIR = "html";
    private final static String REMOTE_URL = "http://localhost:8080/version";

    private Context context;

    public LocalAppStorageManagerImpl(Context context) {
        this.context = context;
    }

    @Override
    public void format() {
        Log.d("DEBUG", "Formatting");
        File internalPath = getPath();
        try {
            FileUtils.deleteDirectory(internalPath);
        } catch (IOException e) {
            Log.e("ERROR", "IOException when formatting local app storage with path "
                    + internalPath, e);
        }
    }

    @Override
    public File getPath() {
        File file = new File (getInternalMemoryPath() + "/version");
        if (file.exists()) {
            return new File(getInternalMemoryPath());
        }
        // Fallback to assets
        return new File(getAssetsPath());
    }

    @Override
    public boolean isUpdated() {
        File file = new File (getPath() + "/version");
        if (file.exists()) {
            try {
                Scanner scanner = new Scanner(file);
                int version = scanner.nextInt();
                int remoteVersion = getRemoteVersion();
                if (version >= remoteVersion) {
                    return true;
                }
            } catch (FileNotFoundException e) {
                // This should not happen
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public void update() {
        // TODO Implement this
        Log.d("DEBUG", "Updating from remote ... (TBD)");
    }

    private int getRemoteVersion() {
        URL url = null;
        try {
            url = new URL(REMOTE_URL + "/version");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(url.openStream()));
            Scanner scanner = new Scanner(in);
            return scanner.nextInt();

        } catch (MalformedURLException e) {
            Log.e("ERROR", "Malformed URL " + REMOTE_URL, e);
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("ERROR", "IOException when reading version from " + REMOTE_URL, e);
            e.printStackTrace();
        }
        return -1;
    }

    private String getInternalMemoryPath() {
        return context.getFilesDir().toString() + "/" + DIR;
    }

    private String getAssetsPath() {
        // TODO Try to get this PATH programmatically
        return "file:///android_asset/" + DIR;
    }
}
