package net.nebur.basewebapp.tasks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import net.nebur.basewebapp.activities.MainActivity;
import net.nebur.basewebapp.activities.SplashScreenActivity;
import net.nebur.basewebapp.storage.LocalAppStorageManager;
import net.nebur.basewebapp.utils.NetworkUtils;

/**
 * Asynchronous task to process all operation related to local app storage.
 */
public class LocalAppStorageTask extends AsyncTask<Void, Integer, Boolean> {
    private LocalAppStorageManager manager;
    private Context context;
    private Activity activity;

    public LocalAppStorageTask(LocalAppStorageManager manager, Context context, Activity activity) {
        this.manager = manager;
        this.context = context;
        this.activity = activity;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        Log.d("DEBUG", "Initializing local app storage check ...");
        if (NetworkUtils.hasConnectivity(context)) {
            Log.d("DEBUG", "Connectivity available!");
            if (!manager.isUpdated()) {
                Log.d("DEBUG", "Outdated! Trying to update ...");
                manager.update();
            }
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        if (!SplashScreenActivity.isRunning) {
            Intent intent = new Intent(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
        activity.finish();
    }
}
