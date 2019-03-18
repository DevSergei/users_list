package me.bkkn.users.users;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class UserPresenter {
    public interface View {
        void onUserListLoaded(List<User> userList);

        void onError(String errorMessage);
    }

    private UserModel userModel;
    private View view;
    private Disposable disposable;
    private List<User> userList;

    public UserPresenter(UserModel userModel) {
        this.userModel = userModel;
    }

    public void attachView(View view) {
        this.view = view;
        if (userList != null)
            view.onUserListLoaded(userList);
        else
            loadUsers();
    }

    public void detachView() {
        this.view = null;
    }

    public void stopLoading() {
        if (disposable != null) {
            disposable.dispose();
        }
    }

    private void loadUsers() {
        disposable = userModel.getUsers()
            .delay(5, TimeUnit.SECONDS)
            .doOnSuccess(users -> userList = users)
//                .doOnSuccess(users-> Collections.sort(users,(user1,user2)-> user1.getName().compareTo(user2.getName())))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .filter(usera -> view != null)
            .subscribe(users -> view.onUserListLoaded(users),
                    error -> view.onError(error.getMessage()));
    }
}
