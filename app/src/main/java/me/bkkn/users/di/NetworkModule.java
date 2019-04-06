package me.bkkn.users.di;

import com.readystatesoftware.chuck.ChuckInterceptor;

import dagger.Module;
import dagger.Provides;
import me.bkkn.users.user.github.GitHubService;
import me.bkkn.users.user.overflow.StackOverFlowService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    @Provides
//    @PerApplication
    GitHubService provideGitHubService(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(GitHubService.class);
    }
    @Provides
//    @PerApplication
    StackOverFlowService provideStackOverflowService(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl("https://api.stackexchange.com/2.2/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(StackOverFlowService.class);
    }
    @Provides
    OkHttpClient provideOkHttpClient() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
        return okHttpClient;
    }


}
