package me.bkkn.users.user;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.bkkn.users.App;
import me.bkkn.users.R;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

public class UsersFragment extends Fragment implements UserPresenter.View {

    public static final String KEY = "USER_MODEL";
    public static final String GITHUB = "GitHub";
    public static final String STACK_OVERFLOW = "Stack";

    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    @BindView(R.id.progress)
    ProgressBar progressBar;

    private String key;
    private Unbinder unbinder;
    private UserRecycleAdapter adapter;
    @Inject UserPresenter presenter;

    public static UsersFragment newInstance(String modelName) {
        Bundle args = new Bundle();
        args.putString(KEY, modelName);
        UsersFragment fragment = new UsersFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_git_hub_users, container, false);
        ((App)getActivity().getApplication()).getAppComponent().createUserComponent().injectUserFragment(this);
        unbinder = ButterKnife.bind(this, view);
        adapter = new UserRecycleAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        key = getArguments().getString(KEY);
//        if (key.equals(GITHUB)) {
//        }else{
//            userModel = new OverflowUsersUserModel(getStackOverFlowService);
//        }
        presenter.attachView(this);

        getLifecycle().addObserver(presenter);

        /*LiveData draft below: */

        class MyLiveData extends MutableLiveData<String>{
            @Override
            protected void onActive() {
                super.onActive();
                Log.d("DDD","has subscribers");
            }

            @Override
            protected void onInactive() {
                super.onInactive();
                Log.d("DDD","no subscribers");
            }
        }

        LiveData<String> liveData = new MyLiveData();
        liveData.observe(this, string -> {
            Log.d("DDD",string);
        });


        new Handler().postDelayed(()-> liveData.getValue(),300 );


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
        if (isRemoving()) {
            presenter.stopLoading();
        }
        unbinder.unbind();
    }

    @Override
    public void onUserListLoaded(List<User> userList) {
        adapter.addUsers(userList);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onError(String errorMessage) {
        Toast.makeText(getContext(), R.string.error_message, Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.GONE);

    }
}
