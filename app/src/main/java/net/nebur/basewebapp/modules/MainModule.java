package net.nebur.basewebapp.modules;

/**
 * App module
 */

import net.nebur.basewebapp.MainApplication;
import net.nebur.basewebapp.WebappConfig;
import net.nebur.basewebapp.activities.MainActivity;
import net.nebur.basewebapp.storage.LocalAppStorageManager;
import net.nebur.basewebapp.storage.LocalAppStorageManagerImpl;
import net.nebur.basewebapp.tasks.LocalAppStorageTask;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module (
        injects = {
                MainActivity.class
        },
        addsTo = AppModule.class
)
public class MainModule {

    private MainActivity activity;

    public MainModule(MainActivity activity) {
        this.activity = activity;
    }

    @Provides @Singleton public MainActivity provideActivity() {
        return activity;
    }
}
