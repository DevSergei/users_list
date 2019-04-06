package me.bkkn.users;

import android.app.Application;
import me.bkkn.users.di.ContextModule;

public class App extends Application {

    AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .contextModule(new ContextModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
