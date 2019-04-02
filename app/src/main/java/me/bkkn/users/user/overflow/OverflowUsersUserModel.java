package me.bkkn.users.user.overflow;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import me.bkkn.users.user.UserModel;
import me.bkkn.users.user.User;

public class OverflowUsersUserModel implements UserModel {

    private StackOverFlowService stackOverFlowService;

    public OverflowUsersUserModel(StackOverFlowService gitHubService) {
        this.stackOverFlowService = gitHubService;
    }

    @Override
    public Single<List<User>> getUsers() {
        return stackOverFlowService.getUsers(1, "stackoverflow")
                .map(UsersResponse::getItems)
                .flatMapObservable(Observable::fromIterable)
                .map(StackOverflowUser::mapToUser)
                .toList();
    }

    public final class UsersResponse {
        private ArrayList<StackOverflowUser> items;

        public UsersResponse(ArrayList<StackOverflowUser> items) {
            this.items = items;
        }

        public ArrayList<StackOverflowUser> getItems() {
            return items;
        }
    }

}
