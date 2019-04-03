package me.bkkn.users;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.readystatesoftware.chuck.ChuckInterceptor;

import java.util.HashMap;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import me.bkkn.users.db.AppDatabase;
import me.bkkn.users.user.UserDataBase;
import me.bkkn.users.user.UserPresenter;
import me.bkkn.users.user.github.GitHubService;
import me.bkkn.users.user.overflow.StackOverFlowService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {

    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE USER ADD COLUMN age INTEGER DEFAULT 0");
        }
    };

    GitHubService gitHubService;
    StackOverFlowService stackOverFlowService;
    HashMap<String, UserPresenter> userPresenters = new HashMap<>();
    SharedPreferences preferences;
    AppDatabase database;
    AppComponent appComponent;

    @Inject OkHttpClient okHttpClient;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.create();
        appComponent.injectApp(this);
        database = Room.databaseBuilder(this, AppDatabase.class, "MyDatabase")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .addMigrations(MIGRATION_1_2)
                .build();
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        createNetworkServices();
    }

    public AppDatabase getDatabase() {
        return database;
    }

    public GitHubService getGitHubService() {
        return gitHubService;
    }

    public StackOverFlowService getStackOverFlowService() {
        return stackOverFlowService;
    }

    private void createNetworkServices() {
//        OkHttpClient okHttpClient = appComponent.getOkHttpClient();
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

    public UserPresenter getUserPresenter(String key) {
        return userPresenters.get(key);
    }

    //   public SQLiteDatabase getUserDatabase() {
//        return userDatabase;
    //   }
}
