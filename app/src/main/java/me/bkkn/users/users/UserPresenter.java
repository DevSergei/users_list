package me.bkkn.users.users;

import android.widget.Toast;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.bkkn.users.R;

public class UserPresenter {
    public interface View {
        void onUserListLoaded(List<User> userList);
        void onError(String errorMessage);
    }

    private UserModel userModel;
    private View view;
    private Disposable disposable;

    public UserPresenter(UserModel userModel, View view) {
        this.userModel = userModel;
        this.view = view;
    }

    public void loadUsers(){

        disposable = userModel.getUsers()
                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(users ->view.onUserListLoaded(users),
                        error-> view.onError(error.getMessage()));
    }

    public void stopLoading(){
        if(disposable!=null){
            disposable.dispose();
        }
    }
}
