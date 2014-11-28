package net.nebur.basewebapp.tasks;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import net.nebur.basewebapp.WebWrappListener;
import net.nebur.basewebapp.storage.LocalAppStorageManager;
import net.nebur.basewebapp.utils.NetworkUtils;

/**
 * Asynchronous task to process all operation related to local app storage.
 */
public class LocalStorageUpdaterTask extends AsyncTask<Void, LocalStorageUpdaterTask.Status, Boolean> {

    public enum Status {
        CHECKING_CONNECTIVITY,
        CHECKING_REMOTE_VERSION,
        UPDATE_AVAILABLE
    }

    private Application application;
    private LocalAppStorageManager manager;
    private WebWrappListener listener;

    public LocalStorageUpdaterTask(Application application, LocalAppStorageManager manager, WebWrappListener listener) {
        this.application = application;
        this.manager = manager;
        this.listener = listener;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        // TODO Review this publish-progress calls
        Log.d("DEBUG", "Initializing local app storage check ...");
        publishProgress(Status.CHECKING_CONNECTIVITY);
        if (NetworkUtils.hasConnectivity(application)) {
            Log.d("DEBUG", "Connectivity available!");
            publishProgress(Status.CHECKING_REMOTE_VERSION);
            if (!manager.isUpdated()) {
                Log.d("DEBUG", "Outdated! Trying to update ...");
                publishProgress(Status.UPDATE_AVAILABLE);
                manager.update();
            }
        }
        return true;
    }

    @Override
    protected void onProgressUpdate(Status... statuses) {
        super.onProgressUpdate(statuses);

        if (statuses.length > 0) {
            Status status = statuses[0];
            switch (status) {
                case CHECKING_CONNECTIVITY:
                    listener.onStart();
                    break;
                case CHECKING_REMOTE_VERSION:
                    listener.onCheckingVersion();
                    break;
                case UPDATE_AVAILABLE:
                    listener.onUpdateAvailable();
                    break;
                default:
                    listener.onUnknownEvent(status.ordinal());
            }
        }
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        listener.onReady(manager.getPath());
    }
}
