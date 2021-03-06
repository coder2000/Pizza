package ca.dieterlunn.android.pizza.app.modules;

import android.content.Context;

import javax.inject.Singleton;

import ca.dieterlunn.android.pizza.app.PizzaApplication;
import ca.dieterlunn.android.pizza.ui.activity.MainActivity;
import dagger.Module;
import dagger.Provides;

@Module(
        injects = {
                PizzaApplication.class,
                MainActivity.class
        },
        library = true,
        complete = false
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
}
