package me.bkkn.users.user;

import android.util.Log;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.bkkn.users.db.AppDatabase;
import me.bkkn.users.db.UserDao;

public class UserPresenter {
    public interface View {
        void onUserListLoaded(List<User> userList);

        void onError(String errorMessage);
    }

    private UserModel userModel;
    private AppDatabase database;
    private View view;
    private Disposable disposable;
    private List<User> userList;
    private UserDao userDao;

    public UserPresenter(UserModel userModel, AppDatabase database) {
        this.userModel = userModel;
        this.database = database;
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
//                .delay(5, TimeUnit.SECONDS)
                .doOnSuccess(list -> userList = list)
                .doOnSuccess(list -> database.userDao().deleteMany(list))
                .doOnSuccess(list -> database.userDao().insertMany(list))
//                .doOnSuccess(users-> Collections.sort(users,(user1,user2)-> user1.getName().compareTo(user2.getName())))
                .doOnError(error -> Log.d("DDDD", error.getMessage()))
                .onErrorResumeNext(error -> database.userDao().getAllUsers()
//                        .observe(this, userList)
                        .flatMap(users -> users.isEmpty() ? Single.error(new RuntimeException("No users in DB")) : Single.just(users))
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(users -> view != null)
                .subscribe(users -> view.onUserListLoaded(users), error -> view.onError(error.getMessage()));
    }
}
