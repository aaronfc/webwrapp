package net.nebur.webwrapp.core.tasks;

import android.content.Context;

import net.nebur.webwrapp.WebWrappListener;
import net.nebur.webwrapp.core.storage.LocalAppStorageManager;

import javax.inject.Inject;

/**
 * Factory for local storage updater task
 */
public class LocalStorageUpdaterTaskFactory {

    private Context context;
    private LocalAppStorageManager manager;

    @Inject
    public LocalStorageUpdaterTaskFactory(Context context, LocalAppStorageManager manager) {
        this.context = context;
        this.manager = manager;
    }

    public LocalStorageUpdaterTask create(WebWrappListener listener) {
        return new LocalStorageUpdaterTask(context, manager, listener);
    }
}
