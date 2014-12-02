package net.nebur.webwrapp.di;

/**
 * App module
 */

import android.app.Application;
import android.content.Context;

import net.nebur.webwrapp.WebWrapp;
import net.nebur.webwrapp.WebWrappConfig;
import net.nebur.webwrapp.core.storage.LocalAppStorageManager;
import net.nebur.webwrapp.core.storage.LocalAppStorageManagerImpl;
import net.nebur.webwrapp.core.tasks.LocalStorageUpdaterTaskFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module (
        injects = {
                WebWrapp.class
        }
)
public class WebWrappModule {

    private WebWrapp webWrapp;

    public WebWrappModule(WebWrapp webWrapp) {
        this.webWrapp = webWrapp;
    }

    @Provides @Singleton public Context provideContext() {
        return webWrapp.getContext();
    }

    @Provides @Singleton public LocalAppStorageManager provideLocalStorageManager(
            LocalAppStorageManagerImpl implementation) {
        return implementation;
    }
}
