package net.nebur.basewebapp.di;

/**
 * App module
 */

import android.app.Application;

import net.nebur.basewebapp.WebWrapp;
import net.nebur.basewebapp.WebWrappConfig;
import net.nebur.basewebapp.storage.LocalAppStorageManager;
import net.nebur.basewebapp.storage.LocalAppStorageManagerImpl;
import net.nebur.basewebapp.tasks.LocalStorageUpdaterTaskFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module (
        injects = {
                WebWrapp.class,
                WebWrappConfig.class,
                Application.class,
                LocalAppStorageManager.class,
                LocalStorageUpdaterTaskFactory.class
        }
)
public class WebWrappModule {

    private WebWrapp webWrapp;

    public WebWrappModule(WebWrapp webWrapp) {
        this.webWrapp = webWrapp;
    }

    @Provides @Singleton public WebWrapp provideWebWrapp() {
        return webWrapp;
    }

    @Provides @Singleton public Application provideApplication() {
        return webWrapp.getApplication();
    }

    @Provides @Singleton public LocalAppStorageManager provideLocalStorageManager(
            Application application, WebWrappConfig config) {
        return new LocalAppStorageManagerImpl(application, config);
    }

    @Provides @Singleton public LocalStorageUpdaterTaskFactory provideLocalStorageUpdaterTaskFactory(
            Application application, LocalAppStorageManager storageManager) {
        return new LocalStorageUpdaterTaskFactory(application, storageManager);
    }
}
