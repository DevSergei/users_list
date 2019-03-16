package me.bkkn.users;

import android.app.Application;

import com.readystatesoftware.chuck.ChuckInterceptor;

import me.bkkn.users.users.github.GitHubService;
import me.bkkn.users.users.overflow.StackOverFlowService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {

    GitHubService gitHubService;
    StackOverFlowService stackOverFlowService;

    @Override
    public void onCreate() {
        super.onCreate();
        createNetworkServises();
    }

    public GitHubService getGitHubService() {
        return gitHubService;
    }

    public StackOverFlowService getStackOverFlowService() {
        return stackOverFlowService;
    }

    private void createNetworkServises(){
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        ChuckInterceptor chuckInterceptor = new ChuckInterceptor(this);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY))
                .addNetworkInterceptor(chuckInterceptor)
                .build();

        Retrofit gitHubRetrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        gitHubService = gitHubRetrofit.create(GitHubService.class);

        Retrofit stackOverflowRetrofit = new Retrofit.Builder()
                .baseUrl("https://api.stackexchange.com/2.2/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        stackOverFlowService = stackOverflowRetrofit.create(StackOverFlowService.class);

    }

}
