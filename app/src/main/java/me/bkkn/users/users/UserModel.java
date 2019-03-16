package me.bkkn.users.users;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import me.bkkn.users.users.User;

public interface UserModel {

    public Single<List<User>> getUsers();

    public interface ModelResponse<T>{
        public void onSuccess(T response);
        public void onError(Throwable error);
    }
}
