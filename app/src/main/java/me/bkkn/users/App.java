package me.bkkn.users;

import android.app.Application;

public class App extends Application {

    AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .setContextBuilder(this)
                .createComponent();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
