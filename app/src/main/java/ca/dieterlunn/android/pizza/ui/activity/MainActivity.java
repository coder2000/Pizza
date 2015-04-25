package ca.dieterlunn.android.pizza.ui.activity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import com.heinrichreimersoftware.materialdrawer.DrawerFrameLayout;
import com.heinrichreimersoftware.materialdrawer.structure.DrawerItem;
import com.heinrichreimersoftware.materialdrawer.structure.DrawerProfile;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import ca.dieterlunn.android.pizza.AccountUtils;
import ca.dieterlunn.android.pizza.R;

public class MainActivity extends BaseActivity {

    @Inject public AccountUtils.UserProfile _profile;
    @Inject public Context _context;
    public RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        DrawerFrameLayout drawer = (DrawerFrameLayout) findViewById(R.id.drawer);
        recyclerView = (RecyclerView) findViewById(R.id.orderList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
                this,
                drawer,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_closed
        ){
            public void onDrawerOpened(View view) {
                invalidateOptionsMenu();
            }

            public void onDrawerClosed(View view) {
                invalidateOptionsMenu();
            }
        };

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawer.setDrawerListener(drawerToggle);

        Drawable avatar = null;

        Picasso.with(_context).load(_profile.get_possiblePhoto()).placeholder(avatar);

        drawer.setProfile(new DrawerProfile()
                .setBackground(getResources().getDrawable(R.drawable.cardboard_texture))
                .setAvatar(avatar)
                .setName(_profile.get_possibleNames().get(0))
                .setDescription(_profile.get_primaryEmail()));

        drawer.addItem(new DrawerItem().setTextPrimary(getString(R.string.orders)));
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
}
