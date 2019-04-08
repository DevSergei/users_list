package me.bkkn.users.user;

import java.util.List;

import io.reactivex.Single;

public interface UserModel {

    public Single<List<User>> getUsers();

    public interface ModelResponse<T> {
        public void onSuccess(T response);

        public void onError(Throwable error);
    }
}
