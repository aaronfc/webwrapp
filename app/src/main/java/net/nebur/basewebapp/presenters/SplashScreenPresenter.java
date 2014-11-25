package net.nebur.basewebapp.presenters;

import android.os.AsyncTask;

import net.nebur.basewebapp.R;
import net.nebur.basewebapp.activities.SplashScreenActivity;
import net.nebur.basewebapp.tasks.LocalAppStorageTask;
import net.nebur.basewebapp.utils.TextUtils;

import javax.inject.Inject;

/**
 * SplashScreen presenter
 */
public class SplashScreenPresenter {

    private SplashScreenActivity view;
    private LocalAppStorageTask storageTask;
    private TextUtils textUtils;

    public SplashScreenPresenter(SplashScreenActivity view, LocalAppStorageTask storageTask, TextUtils textUtils) {
        this.view = view;
        this.storageTask = storageTask;
        this.textUtils = textUtils;
    }

    public void warmUp() {
        view.setDescription(textUtils.get(R.string.starting_warm_up));
        storageTask.execute();
    }
}
