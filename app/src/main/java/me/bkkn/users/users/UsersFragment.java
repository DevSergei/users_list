package me.bkkn.users.users;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
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

    public static final String GITHUB = "GITHUB";
    private static UserPresenter presenter;
    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    @BindView(R.id.progress)
    ProgressBar progressBar;

    private Unbinder unbinder;
    private UserRecycleAdapter adapter;

    public static UsersFragment newInstance(boolean isGitHub) {
        Bundle args = new Bundle();
        args.putBoolean(GITHUB, isGitHub);
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
        if (getArguments().getBoolean(GITHUB)) {
            userModel = new GitHubUsersUserModel(((App) getActivity().getApplication()).getGitHubService());
        }else{
            userModel = new OverflowUsersUserModel(((App) getActivity().getApplication()).getStackOverFlowService());
        }

        if (presenter == null) {
            presenter = new UserPresenter(userModel);
        }
        presenter.attachView(this);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
        if (getActivity().isFinishing()) {
            presenter.stopLoading();
            presenter = null;
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
