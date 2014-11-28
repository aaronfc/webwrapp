package net.nebur.basewebapp.tasks;

import android.app.Application;

import net.nebur.basewebapp.WebWrappListener;
import net.nebur.basewebapp.storage.LocalAppStorageManager;

import javax.inject.Inject;

/**
 * Factory for local storage updater task
 */
public class LocalStorageUpdaterTaskFactory {

    private Application application;
    private LocalAppStorageManager manager;

    @Inject
    public LocalStorageUpdaterTaskFactory(Application application, LocalAppStorageManager manager) {
        this.application = application;
        this.manager = manager;
    }

    public LocalStorageUpdaterTask create(WebWrappListener listener) {
        return new LocalStorageUpdaterTask(application, manager, listener);
    }
}
