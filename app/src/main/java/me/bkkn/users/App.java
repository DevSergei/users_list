package me.bkkn.users;

import android.app.Application;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;

import com.readystatesoftware.chuck.ChuckInterceptor;

import java.io.IOException;
import java.util.HashMap;

import me.bkkn.users.network.AddTokenInterceptor;
import me.bkkn.users.network.ReceivedTokenInterceptor;
import me.bkkn.users.users.UserDataBase;
import me.bkkn.users.users.UserPresenter;
import me.bkkn.users.users.github.GitHubService;
import me.bkkn.users.users.overflow.StackOverFlowService;
import okhttp3.Authenticator;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {

    GitHubService gitHubService;
    StackOverFlowService stackOverFlowService;
    HashMap<String, UserPresenter> userPresenters = new HashMap<>();
    SharedPreferences preferences;
    UserDataBase userDatabaseHelper;
    SQLiteDatabase userDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        userDatabaseHelper = new UserDataBase(this);
        userDatabase = userDatabaseHelper.getWritableDatabase();
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        createNetworkServices();
    }

    public GitHubService getGitHubService() {
        return gitHubService;
    }

    public StackOverFlowService getStackOverFlowService() {
        return stackOverFlowService;
    }

    private void createNetworkServices() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        ChuckInterceptor chuckInterceptor = new ChuckInterceptor(this);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY))
                .addNetworkInterceptor(chuckInterceptor)
//                .addInterceptor(new ReceivedTokenInterceptor(preferences))
//                .addInterceptor(new AddTokenInterceptor(preferences))
//                .authenticator((route, response) -> {
//                    if (response.code() == 401) {
////                        logout();
//                    }
//                    return null;
//                })
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

    public void setUserPresenter(String key, UserPresenter userPresenter) {
        if (userPresenter == null) {
            userPresenters.remove(key);
            return;
        }
        userPresenters.put(key, userPresenter);
    }
    public UserPresenter getUserPresenter(String key){
        return userPresenters.get(key);
    }

    public SQLiteDatabase getUserDatabase() {
        return userDatabase;
    }
}
