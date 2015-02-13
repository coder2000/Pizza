package ca.dieterlunn.android.pizza.ui.activity;

import android.content.IntentSender;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;

import butterknife.InjectView;
import ca.dieterlunn.android.pizza.R;
import ca.dieterlunn.android.pizza.callbacks.NavigationDrawerCallbacks;
import ca.dieterlunn.android.pizza.fragments.NavigationDrawerFragment;

/**
 * Created by Dieter on 12/21/2014.
 */
public class MainActivity extends BaseActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, NavigationDrawerCallbacks {

    private static final int RC_SIGN_IN = 0;

    @InjectView(R.id.toolbar) public Toolbar toolbar;
    private GoogleApiClient mClient;

    private NavigationDrawerFragment navigationDrawerFragment;

    private boolean mIntentInProgress;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);

        setContentView(R.layout.activity_main);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .build();

        navigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.drawer_fragment);
        navigationDrawerFragment.setup(R.id.drawer_fragment, (DrawerLayout)findViewById(R.id.drawer), toolbar);
    }

    @Override
    protected void onStart() {
        super.onStart();

        mClient.connect();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {
        mClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (!mIntentInProgress && connectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                startIntentSenderForResult(connectionResult.getResolution().getIntentSender(), RC_SIGN_IN, null, 0, 0, 0);
            } catch (IntentSender.SendIntentException e) {
                mIntentInProgress = false;
                mClient.connect();
            }
        }
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
