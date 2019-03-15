package me.bkkn.users;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.bkkn.users.github.GitHubService;
import me.bkkn.users.github.GitHubUsersModel;
import me.bkkn.users.overflow.OverflowUsersModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

public class UsersFragment extends Fragment {

    public static final String GITHUB = "GITHUB";
    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    private Unbinder unbinder;
    private UserRecycleAdapter adapter;
    private Model model;

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

        if (getArguments().getBoolean(GITHUB)) {
            model = new GitHubUsersModel(((App) getActivity().getApplication()).getGitHubService());
        }else{
            model = new OverflowUsersModel(((App) getActivity().getApplication()).getStackOverFlowService());
        }

        adapter = new UserRecycleAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        model.getUsers(new Model.ModelResponse<List<User>>() {
            @Override
            public void onSuccess(List<User> response) {
                adapter.addUsers(response);
            }

            @Override
            public void onError(Throwable error) {
                Toast.makeText(getContext(), R.string.error_message, Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
