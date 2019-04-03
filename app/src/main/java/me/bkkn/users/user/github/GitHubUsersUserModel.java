package me.bkkn.users.user.github;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import me.bkkn.users.user.UserModel;
import me.bkkn.users.user.User;

public class GitHubUsersUserModel implements UserModel {
    private GitHubService gitHubService;

    public GitHubUsersUserModel(GitHubService gitHubService) {
        this.gitHubService = gitHubService;
    }

    @Override
    public Single<List<User>> getUsers() {
        return gitHubService.getUsers(0)
                .flatMapObservable(Observable::fromIterable)
                .map(GitHubUser::mapToUser)
                .toList();
    }
}
