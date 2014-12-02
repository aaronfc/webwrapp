package net.nebur.webwrapp.core.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import net.nebur.webwrapp.WebWrappListener;
import net.nebur.webwrapp.core.storage.LocalAppStorageManager;
import net.nebur.webwrapp.utils.NetworkUtils;

/**
 * Asynchronous task to process all operation related to local app storage.
 */
public class LocalStorageUpdaterTask extends AsyncTask<Void, LocalStorageUpdaterTask.Status, Boolean> {

    public enum Status {
        STARTING,
        CHECKING_REMOTE_VERSION,
        UPDATING
    }

    private Context context;
    private LocalAppStorageManager manager;
    private WebWrappListener listener;

    public LocalStorageUpdaterTask(Context context, LocalAppStorageManager manager, WebWrappListener listener) {
        this.context = context;
        this.manager = manager;
        this.listener = listener;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        // TODO Review this publish-progress calls
        Log.d("DEBUG", "Initializing local app storage check ...");
        publishProgress(Status.STARTING);
        if (NetworkUtils.hasConnectivity(context)) {
            Log.d("DEBUG", "Connectivity available!");
            publishProgress(Status.CHECKING_REMOTE_VERSION);
            if (!manager.isUpdated()) {
                Log.d("DEBUG", "Outdated! Trying to update ...");
                publishProgress(Status.UPDATING);
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
                case STARTING:
                    listener.onWarmUpStart();
                    break;
                case CHECKING_REMOTE_VERSION:
                    listener.onCheckingVersion();
                    break;
                case UPDATING:
                    listener.onUpdating();
                    break;
                default:
                    listener.onUnknownEvent(status.ordinal());
            }
        }
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        listener.onReady(manager.getIndexFullPath());
    }
}
