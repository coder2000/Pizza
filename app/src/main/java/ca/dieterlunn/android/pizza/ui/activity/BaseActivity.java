package ca.dieterlunn.android.pizza.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import butterknife.ButterKnife;
import ca.dieterlunn.android.pizza.di.PizzaApplication;

/**
 * Created by Dieter on 2/12/2015.
 */
public class BaseActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectDependencies();
        injectViews();
    }

    private void injectDependencies() {
        ((PizzaApplication) getApplicationContext()).inject(this);
    }

    private void injectViews() {
        ButterKnife.inject(this);
    }
}
