package ca.dieterlunn.android.pizza.di;

import android.app.Application;

import ca.dieterlunn.android.pizza.di.modules.RootModule;
import dagger.ObjectGraph;

/**
 * Created by Dieter on 2/12/2015.
 */
public class PizzaApplication extends Application {
    private ObjectGraph objectGraph;

    @Override
    public void onCreate() {
        super.onCreate();
        injectDependencies();
    }

    private void injectDependencies() {
        objectGraph = ObjectGraph.create(new RootModule(this));
        objectGraph.inject(this);
    }

    public void inject(Object object) {
        objectGraph.inject(object);
    }
}
