package net.nebur.basewebapp.di;

/**
 * App module
 */

import net.nebur.basewebapp.activity.MainActivity;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module (
        injects = {
                MainActivity.class
        },
        addsTo = WebWrappModule.class
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
