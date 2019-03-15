package me.bkkn.users;

import java.util.List;

public interface Model {

    public void getUsers(Model.ModelResponse<List<User>> modelResponse);

    public interface ModelResponse<T>{
        public void onSuccess(T response);
        public void onError(Throwable error);
    }
}
