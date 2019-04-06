package me.bkkn.users.di;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import me.bkkn.users.App;
import okhttp3.logging.HttpLoggingInterceptor;

@Module
public class ContextModule {
    private Context context;

    public ContextModule(Context context) {
        this.context = context;
    }

    @Provides
    Context provideContext() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        return context;
    }

}
