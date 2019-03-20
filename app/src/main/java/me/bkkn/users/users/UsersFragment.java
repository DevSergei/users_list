package me.bkkn.users.users;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.bkkn.users.App;
import me.bkkn.users.R;
import me.bkkn.users.users.github.GitHubUsersUserModel;
import me.bkkn.users.users.overflow.OverflowUsersUserModel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

public class UsersFragment extends Fragment implements UserPresenter.View {

    public static final String KEY = "USER_MODEL";
    public static final String GITHUB = "GitHub";
    public static final String STACK_OVERFLOW = "Stack";

    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    @BindView(R.id.progress)
    ProgressBar progressBar;

    private Unbinder unbinder;
    private UserRecycleAdapter adapter;
    private UserPresenter presenter;
    private String key;

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
        unbinder = ButterKnife.bind(this, view);
        adapter = new UserRecycleAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        UserModel userModel;
        key = getArguments().getString(KEY);
        if (key.equals(GITHUB)) {
            userModel = new GitHubUsersUserModel(((App) getActivity().getApplication()).getGitHubService());
        }else{
            userModel = new OverflowUsersUserModel(((App) getActivity().getApplication()).getStackOverFlowService());
        }
        presenter = ((App) getActivity().getApplication()).getUserPresenter(key);
        if (presenter == null) {
            presenter = new UserPresenter(userModel, (((App) getActivity().getApplication()).getUserDatabase()));
            ((App) getActivity().getApplication()).setUserPresenter(key, presenter);
        }
        presenter.attachView(this);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
        if (isRemoving()) {
            presenter.stopLoading();
            presenter = null;
            ((App) getActivity().getApplication()).setUserPresenter(key,null);
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
