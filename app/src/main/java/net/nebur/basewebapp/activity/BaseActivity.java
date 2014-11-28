package net.nebur.basewebapp.activity;

import android.app.Activity;
import android.os.Bundle;

import java.util.List;

import dagger.ObjectGraph;

/**
 * BaseActivity class
 */
public abstract class BaseActivity extends Activity {
    private ObjectGraph activityGraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startInjection();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityGraph = null;
    }

    private void startInjection() {
        activityGraph = ObjectGraph.create(getModules().toArray());
        activityGraph.inject(this);
    }

    protected abstract List<Object> getModules();
}
