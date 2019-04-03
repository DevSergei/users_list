package me.bkkn.users;

import dagger.Component;
import me.bkkn.users.di.NetworkModule;
import okhttp3.OkHttpClient;

@Component(modules = {NetworkModule.class})
public interface AppComponent {
    void injectApp(App app);
}
