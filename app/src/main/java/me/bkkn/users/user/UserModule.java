package me.bkkn.users.user;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.bkkn.users.db.AppDatabase;
import me.bkkn.users.di.PerFragment;
import me.bkkn.users.user.github.GitHubService;
import me.bkkn.users.user.github.GitHubUsersUserModel;

@Module
public class UserModule {
    @Provides
    @PerFragment
    UserPresenter provideUserPresenter(AppDatabase database, GitHubService gitHubService){
        return new UserPresenter(new GitHubUsersUserModel(gitHubService),database);
    }
}
