package net.nebur.basewebapp.modules;

/**
 * App module
 */

import net.nebur.basewebapp.MainApplication;
import net.nebur.basewebapp.WebappConfig;
import net.nebur.basewebapp.activities.SplashScreenActivity;
import net.nebur.basewebapp.presenters.SplashScreenPresenter;
import net.nebur.basewebapp.storage.LocalAppStorageManager;
import net.nebur.basewebapp.storage.LocalAppStorageManagerImpl;
import net.nebur.basewebapp.tasks.LocalAppStorageTask;
import net.nebur.basewebapp.utils.TextUtils;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module (
        injects = {
                SplashScreenActivity.class,
                LocalAppStorageTask.class
        },
        addsTo = AppModule.class
)
public class SplashScreenModule {

    private SplashScreenActivity view;

    public SplashScreenModule(SplashScreenActivity view) {
        this.view = view;
    }

    @Provides @Singleton public SplashScreenActivity provideActivity() {
        return view;
    }

    @Provides @Singleton public SplashScreenPresenter providePresenter(
            SplashScreenActivity view, LocalAppStorageTask storageTask, TextUtils textUtils) {
        return new SplashScreenPresenter(view, storageTask, textUtils);
    }

    @Provides @Singleton public LocalAppStorageTask provideLocalStorageTask(
            LocalAppStorageManager storageManager, SplashScreenActivity splashActivity,
            TextUtils textUtils) {
        return new LocalAppStorageTask(storageManager, splashActivity, textUtils);
    }
}
