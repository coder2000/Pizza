package ca.dieterlunn.android.pizza.di.modules;

import android.content.Context;

import com.squareup.otto.Bus;

import javax.inject.Singleton;

import ca.dieterlunn.android.pizza.di.PizzaApplication;
import ca.dieterlunn.android.pizza.ui.activity.MainActivity;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Dieter on 2/12/2015.
 */

@Module(
        injects = {
                PizzaApplication.class,
                MainActivity.class
        },
        library = true
)

public class RootModule {
    private final Context _context;

    public RootModule(Context context) {
        _context = context;
    }

    @Provides
    @Singleton
    public Context provideApplicationContext() {
        return _context;
    }

    @Provides
    @Singleton
    public Bus provideBus() {
        return new Bus();
    }
}