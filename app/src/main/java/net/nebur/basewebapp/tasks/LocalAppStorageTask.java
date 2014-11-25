package net.nebur.basewebapp.tasks;

import android.os.AsyncTask;
import android.util.Log;

import net.nebur.basewebapp.R;
import net.nebur.basewebapp.activities.SplashScreenActivity;
import net.nebur.basewebapp.storage.LocalAppStorageManager;
import net.nebur.basewebapp.utils.NetworkUtils;
import net.nebur.basewebapp.utils.TextUtils;

import javax.inject.Inject;

/**
 * Asynchronous task to process all operation related to local app storage.
 */
public class LocalAppStorageTask extends AsyncTask<Void, String, Boolean> {
    private LocalAppStorageManager manager;
    private SplashScreenActivity view;
    private TextUtils textUtils;

    @Inject
    public LocalAppStorageTask(LocalAppStorageManager manager, SplashScreenActivity view, TextUtils textUtils) {
        this.manager = manager;
        this.view = view;
        this.textUtils = textUtils;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        // TODO Review this publish-progress calls
        Log.d("DEBUG", "Initializing local app storage check ...");
        publishProgress(textUtils.get(R.string.checking_connectivity));
        if (NetworkUtils.hasConnectivity(view)) {
            Log.d("DEBUG", "Connectivity available!");
            publishProgress(textUtils.get(R.string.checking_version));
            if (!manager.isUpdated()) {
                Log.d("DEBUG", "Outdated! Trying to update ...");
                publishProgress(textUtils.get(R.string.updating));
                manager.update();
            } else {
                publishProgress(textUtils.get(R.string.already_last_version));
            }
        }
        return true;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);

        if (values.length > 0)
            view.setDescription(values[0]);
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        view.goToMainScreen();
    }
}
