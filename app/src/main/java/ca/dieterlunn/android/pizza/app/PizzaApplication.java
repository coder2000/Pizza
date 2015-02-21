package ca.dieterlunn.android.pizza.app;

import android.app.Application;

import ca.dieterlunn.android.pizza.app.modules.RootModule;
import dagger.ObjectGraph;

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
