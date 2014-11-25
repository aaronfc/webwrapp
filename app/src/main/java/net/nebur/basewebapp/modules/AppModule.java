package net.nebur.basewebapp.modules;

/**
 * App module
 */

import android.content.Context;

import net.nebur.basewebapp.MainApplication;
import net.nebur.basewebapp.WebappConfig;
import net.nebur.basewebapp.storage.LocalAppStorageManager;
import net.nebur.basewebapp.storage.LocalAppStorageManagerImpl;
import net.nebur.basewebapp.utils.TextUtils;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module (
        injects = {
                MainApplication.class,
                WebappConfig.class,
                LocalAppStorageManager.class,
                TextUtils.class
        }
)
public class AppModule {

    private MainApplication app;

    public AppModule(MainApplication app) {
        this.app = app;
    }

    @Provides @Singleton public MainApplication provideApplicationContext() {
        return app;
    }

    @Provides @Singleton public LocalAppStorageManager provideLocalStorageManager(
            MainApplication application, WebappConfig config) {
        return new LocalAppStorageManagerImpl(application, config);
    }
}
