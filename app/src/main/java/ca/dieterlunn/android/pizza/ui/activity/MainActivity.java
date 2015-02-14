package ca.dieterlunn.android.pizza.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import butterknife.InjectView;
import ca.dieterlunn.android.pizza.R;
import ca.dieterlunn.android.pizza.callbacks.NavigationDrawerCallbacks;
import ca.dieterlunn.android.pizza.fragments.NavigationDrawerFragment;

/**
 * Created by Dieter on 12/21/2014.
 */
public class MainActivity extends BaseActivity implements NavigationDrawerCallbacks {

    @InjectView(R.id.toolbar) public Toolbar toolbar;

    private NavigationDrawerFragment navigationDrawerFragment;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);

        setContentView(R.layout.activity_main);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        navigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.drawer_fragment);
        navigationDrawerFragment.setup(R.id.drawer_fragment, (DrawerLayout)findViewById(R.id.drawer), toolbar);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        if (navigationDrawerFragment.isDrawerOpen()) {
            navigationDrawerFragment.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

    }
}
