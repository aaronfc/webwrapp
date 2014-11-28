package net.nebur.basewebapp;

import android.app.Application;

import net.nebur.basewebapp.di.WebWrappModule;
import net.nebur.basewebapp.tasks.LocalStorageUpdaterTask;
import net.nebur.basewebapp.tasks.LocalStorageUpdaterTaskFactory;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import dagger.ObjectGraph;

/**
 * WebWrapp entry-point
 */
public class WebWrapp {

    private ObjectGraph objectGraph;

    @Inject
    LocalStorageUpdaterTaskFactory updaterTaskFactory;

    private Application application;

    public WebWrapp(Application application) {
        this.application = application;
        startInjection();
    }

    private void startInjection() {
        objectGraph = ObjectGraph.create(getModules().toArray());
        objectGraph.inject(this);
    }

    private List<Object> getModules() {
        return Arrays.<Object>asList(new WebWrappModule(this));
    }

    /**
     * Initialize WebWrapp process. This will try to update the local storage while notifying the
     * listener about possible events.
     *
     * @param listener Lister to be notified
     */
    public void go(WebWrappListener listener) {
        LocalStorageUpdaterTask task = updaterTaskFactory.create(listener);
        task.execute();
    }

    public Application getApplication() {
        return application;
    }
}
