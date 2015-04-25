package ca.dieterlunn.android.pizza.app.modules;

import android.content.Context;

import ca.dieterlunn.android.pizza.AccountUtils;
import dagger.Module;
import dagger.Provides;

@Module(
        includes = {
                RootModule.class
        }
)
public class UserProfileModule {
    @Provides
    public AccountUtils.UserProfile provideUserProfile(Context context) {
        return AccountUtils.getUserProfile(context);
    }
}
