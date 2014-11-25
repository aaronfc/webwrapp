package net.nebur.basewebapp.activities;

import android.app.Activity;
import android.os.Bundle;

import net.nebur.basewebapp.MainApplication;

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
        activityGraph = ((MainApplication) getApplication()).createScopedGraph(getModules().toArray());
        activityGraph.inject(this);
    }

    protected abstract List<Object> getModules();
}
