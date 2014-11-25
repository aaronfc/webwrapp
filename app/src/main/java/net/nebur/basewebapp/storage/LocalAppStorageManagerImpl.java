package net.nebur.basewebapp.storage;

import android.content.Context;
import android.util.Log;

import net.nebur.basewebapp.WebappConfig;

import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * LocalAppStorageManager implementation
 */
public class LocalAppStorageManagerImpl implements LocalAppStorageManager {

    private Context context;
    private WebappConfig config;

    public LocalAppStorageManagerImpl(Context context) {
        this.context = context;
        this.config = new WebappConfig(context);
    }

    @Override
    public void format() {
        Log.d("DEBUG", "Cleaning internal memory folder");
        File internalPath = new File(getInternalMemoryPath());
        if (internalPath.exists()) {
            try {
                FileUtils.cleanDirectory(internalPath);
            } catch (IOException e) {
                Log.e("ERROR", "IOException when formatting local app storage with path "
                        + internalPath, e);
            }
        }
    }

    @Override
    public String getPath() {
        File file = new File (getInternalMemoryPath() + config.getRemoteVersionPath());
        if (file.exists()) {
            return getInternalMemoryPath();
        }
        // Fallback to assets
        return getAssetsPath();
    }

    @Override
    public boolean isUpdated() {
        // File won't exist if path points to assets folder
        File file = new File (getPath() + config.getRemoteVersionPath());
        if (file.exists()) {
            try {
                Scanner scanner = new Scanner(file);
                int version = scanner.nextInt();
                int remoteVersion = getRemoteVersion();
                Log.d("DEBUG", "Local version: " + version);
                Log.d("DEBUG", "Remote version: " + remoteVersion);
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
        Log.d("DEBUG", "Updating from remote ...");
        if (download()) {
            format();
            unzip();
            FileUtils.deleteQuietly(new File(context.getFilesDir().toString() + "/last.zip"));
        }
    }

    private int getRemoteVersion() {
        URL url = null;
        String remoteUrl = config.getRemoteUrl();
        try {
            url = new URL(remoteUrl + config.getRemoteVersionPath());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            if (config.requiresHttpAuth()) {
                Log.d("DEBUG", "Using HTTP_AUTH: " + config.getHttpAuthHeader());
                connection.setRequestProperty("Authorization", config.getHttpAuthHeader());
            }
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            Scanner scanner = new Scanner(in);
            return scanner.nextInt();

        } catch (MalformedURLException e) {
            Log.e("ERROR", "Malformed URL " + remoteUrl);
        } catch (IOException e) {
            Log.e("ERROR", "IOException when reading version from " + url);
        }
        return -1;
    }

    private String getInternalMemoryPath() {
        return context.getFilesDir().toString() + "/" + getContentsDir();
    }

    private String getAssetsPath() {
        // TODO Try to get this PATH programmatically
        return "android_asset/" + getContentsDir();
    }

    private String getContentsDir() {
        return config.getContentsDir();
    }

    /**
     * Download last Webapp version (last.zip) to internal memory.
     */
    private boolean download() {
        Log.d("DEBUG", "Starting remote download process.");
        InputStream input = null;
        OutputStream output = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(config.getRemoteUrl() + config.getRemoteDownloadPath());
            Log.d("DEBUG", "Remote URL: " + url.toString());
            connection = (HttpURLConnection) url.openConnection();
            if (config.requiresHttpAuth()) {
                Log.d("DEBUG", "Using HTTP_AUTH: " + config.getHttpAuthHeader());
                connection.setRequestProperty("Authorization", config.getHttpAuthHeader());
            }
            connection.connect();

            // this will be useful to display download percentage
            // might be -1: server did not report the length
            int fileLength = connection.getContentLength();

            // download the file
            input = connection.getInputStream();
            output = new FileOutputStream(context.getFilesDir().toString() + "/last.zip");
            Log.d("DEBUG", "Storing into: " + context.getFilesDir().toString() + "/last.zip");

            byte data[] = new byte[4096];
            long total = 0;
            int count;
            while ((count = input.read(data)) != -1) {
                // TODO Allow cancellation
                /*if (isCancelled()) {
                    input.close();
                    return null;
                }*/
                total += count;
                // TODO Handle progress
                /*if (fileLength > 0) // only if total length is known
                    publishProgress((int) (total * 100 / fileLength));*/
                output.write(data, 0, count);
            }
            return true;
        } catch (Exception e) {
            Log.e("ERROR", "Error downloading last version.", e);
        } finally {
            try {
                if (output != null)
                    output.close();
                if (input != null)
                    input.close();
            } catch (IOException ignored) {
            }

            if (connection != null)
                connection.disconnect();
        }
        return false;
    }

    public void unzip()
    {
        Log.d("DEBUG", "Starting unzip process");
        String location = getInternalMemoryPath() + "/";
        dirChecker(location);
        try
        {
            FileInputStream fin = new FileInputStream(context.getFilesDir().toString() + "/last.zip");
            ZipInputStream zin = new ZipInputStream(fin);
            ZipEntry ze = null;
            while ((ze = zin.getNextEntry()) != null)
            {
                if(ze.isDirectory())
                {
                    dirChecker(location + ze.getName());
                }
                else
                {
                    Log.d("DEBUG", "Unzipping " + location + ze.getName());
                    FileOutputStream fout = new FileOutputStream(location + ze.getName());
                    byte[] buffer = new byte[8192];
                    int len;
                    while ((len = zin.read(buffer)) != -1)
                    {
                        fout.write(buffer, 0, len);
                    }
                    fout.close();

                    zin.closeEntry();

                }

            }
            zin.close();
            Log.d("DEBUG", "Finished unzipping");
        }
        catch(Exception e)
        {
            Log.e("ERROR", "Unzipping error", e);
        }

    }

    private void dirChecker(String dir)
    {
        File f = new File(dir);
        if(!f.isDirectory())
        {
            f.mkdirs();
        }
    }
}
