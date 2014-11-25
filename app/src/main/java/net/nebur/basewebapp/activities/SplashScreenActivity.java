package net.nebur.basewebapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import net.nebur.basewebapp.R;
import net.nebur.basewebapp.modules.SplashScreenModule;
import net.nebur.basewebapp.presenters.SplashScreenPresenter;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;


/**
 * Splash-screen activity.
 */
public class SplashScreenActivity extends BaseActivity {

    @Inject
    SplashScreenPresenter presenter;

    private TextView textView;

    /**
     * Hacky way to know if activity is running
     */
    public static boolean isRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash_screen);

        textView = (TextView) findViewById(R.id.splash_screen_text);

        presenter.warmUp();
    }

    @Override
    protected List<Object> getModules() {
        return Arrays.<Object>asList(new SplashScreenModule(this));
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

    public void setDescription(String text) {
        textView.setText(text);
    }

    public void goToMainScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
