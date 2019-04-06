package me.bkkn.users;

import dagger.Component;
import me.bkkn.users.di.ContextModule;
import me.bkkn.users.di.DatabaseModule;
import me.bkkn.users.di.NetworkModule;
import me.bkkn.users.user.UserComponent;
import me.bkkn.users.user.UsersFragment;
import okhttp3.OkHttpClient;

@Component(modules = {NetworkModule.class, ContextModule.class, DatabaseModule.class})
public interface AppComponent {
    UserComponent createUserComponent();
}
