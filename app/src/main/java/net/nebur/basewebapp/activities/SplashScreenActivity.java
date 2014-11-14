package net.nebur.basewebapp.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import net.nebur.basewebapp.R;
import net.nebur.basewebapp.storage.LocalAppStorageManager;
import net.nebur.basewebapp.storage.LocalAppStorageManagerImpl;
import net.nebur.basewebapp.tasks.LocalAppStorageTask;


/**
 * Splash-screen activity.
 */
public class SplashScreenActivity extends Activity {
    /**
     * Local app storage asynchronous task
     */
    private LocalAppStorageTask storageTask;

    /**
     * Hacky way to know if activity is running
     */
    public static boolean isRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash_screen);

        Context context = getApplicationContext();
        LocalAppStorageManager storageManager = new LocalAppStorageManagerImpl(context);
        storageTask = new LocalAppStorageTask(storageManager, context, this);
        storageTask.execute();
    }

    @Override
    protected void onStart() {
        super.onStart();
        isRunning = true;
    }


    @Override
    protected void onStop() {
        super.onStop();
        isRunning = false;
    }
}
