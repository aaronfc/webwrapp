package net.nebur.webwrapp;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import java.io.IOException;
import java.util.Properties;

import javax.inject.Inject;

/**
 * WebWrapp configuration handler.
 * TODO Some cleaning up and proper HTTP Authentication implementation
 */
public class WebWrappConfig {
    private static final String file = "webwrapp-config.properties";

    private Properties properties;

    @Inject
    public WebWrappConfig(Context context) {
        properties = new Properties();
        try {
            properties.load(context.getAssets().open(file));
        } catch (IOException e) {
            Log.d("ERROR", "Missing configuration file in the assets folder. File: " + file);
        }
    }

    public String getRemoteUrl() {
        return properties.getProperty("remote_url");
    }

    public String getContentsDir() {
        return properties.getProperty("contents_dir");
    }

    public String getIndexRelativePath() {
        return properties.getProperty("index_relative_path", "index.html");
    }

    public boolean requiresHttpAuth() {
        return properties.getProperty("requires_http_auth", "false").equals("true");
    }

    public String getHttpAuthHeader() {
        String authentication = properties.getProperty("http_auth_user")
                + ":" + properties.getProperty("http_auth_pass");
        return "Basic " + Base64.encodeToString(authentication.getBytes(), Base64.NO_WRAP);
    }

    public String getRemoteVersionPath() {
        return properties.getProperty("remote_version_path");
    }

    public String getRemoteDownloadPath() {
        return properties.getProperty("remote_download_path");
    }
}
