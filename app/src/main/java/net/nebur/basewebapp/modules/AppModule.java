package net.nebur.basewebapp.modules;

/**
 * App module
 */

import android.content.Context;

import net.nebur.basewebapp.MainApplication;
import net.nebur.basewebapp.tasks.LocalAppStorageTask;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module (
    injects = {
            MainApplication.class,
            LocalAppStorageTask.class
    }
)
public class AppModule {

    private MainApplication app;

    public AppModule(MainApplication app) {
        this.app = app;
    }

    @Provides @Singleton public Context provideApplicationContext() {
        return app;
    }
}
