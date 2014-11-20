package net.nebur.basewebapp;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Webapp configuration file.
 */
public class WebappConfig {
    private static final String file = "webapp.properties";

    private Properties properties;

    public WebappConfig(Context context) {
        properties = new Properties();
        try {
            properties.load(context.getAssets().open(file));
        } catch (IOException e) {
            Log.d("ERROR", "Error opening properties file: " + file);
        }
    }

    public String getRemoteUrl() {
        return properties.getProperty("remote_url");
    }

    public String getContentsDir() {
        return properties.getProperty("contents_dir");
    }

    public boolean requiresHttpAuth() {
        return properties.getProperty("requires_http_auth", "no").equals("yes");
    }

    public String getHttpAuthHeader() {
        String authentication = properties.getProperty("http_auth_user") + ":" + properties.getProperty("http_auth_pass");
        return "Basic " + Base64.encodeToString(authentication.getBytes(), Base64.NO_WRAP);
    }

    public String getRemoteVersionPath() {
        return properties.getProperty("remote_version_path");
    }

    public String getRemoteDownloadPath() {
        return properties.getProperty("remote_download_path");
    }
}
