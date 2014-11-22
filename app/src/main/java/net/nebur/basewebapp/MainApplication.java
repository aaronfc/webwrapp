package net.nebur.basewebapp;

import android.app.Application;

import net.nebur.basewebapp.activities.SplashScreenActivity;
import net.nebur.basewebapp.modules.AppModule;
import net.nebur.basewebapp.tasks.LocalAppStorageTask;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import dagger.ObjectGraph;

/**
 * Main application instance.
 */
public class MainApplication extends Application {

    private ObjectGraph objectGraph;

    @Inject
    LocalAppStorageTask localAppStorageTask;

    @Override
    public void onCreate() {
        super.onCreate();
        objectGraph = ObjectGraph.create(getModules().toArray());
        objectGraph.inject(this);
    }

    private List<Object> getModules() {
        return Arrays.<Object>asList(new AppModule(this));
    }
}
