package me.bkkn.users;

import android.content.Context;

import dagger.BindsInstance;
import dagger.Component;
import me.bkkn.users.di.DatabaseModule;
import me.bkkn.users.di.NetworkModule;
import me.bkkn.users.di.PerApplication;
import me.bkkn.users.user.UserComponent;

@PerApplication
@Component(modules = {NetworkModule.class, DatabaseModule.class})
public interface AppComponent {
    Context getContext();
    UserComponent createUserComponent();

    @Component.Builder
    interface MyBuilder {

        AppComponent createComponent();

        @BindsInstance
        MyBuilder setContextBuilder(Context contextBuilder);
    }
}
