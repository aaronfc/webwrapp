package net.nebur.basewebapp.utils;

import net.nebur.basewebapp.MainApplication;

import javax.inject.Inject;

/**
 * TextUtils class
 */
public class TextUtils {

    private MainApplication application;

    @Inject
    public TextUtils(MainApplication application) {
        this.application = application;
    }

    public String get(int id) {
        return application.getResources().getString(id);
    }
}
