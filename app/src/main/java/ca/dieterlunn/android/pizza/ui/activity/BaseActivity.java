package ca.dieterlunn.android.pizza.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import ca.dieterlunn.android.pizza.app.PizzaApplication;

public class BaseActivity extends AppCompatActivity {

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
