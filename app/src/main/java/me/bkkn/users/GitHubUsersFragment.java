package me.bkkn.users;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.readystatesoftware.chuck.ChuckInterceptor;

import java.util.ArrayList;
import java.util.List;

public class GitHubUsersFragment extends Fragment {

    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    private Unbinder unbinder;
    private UserRecycleAdapter adapter;
    private GitHubService gitHubService;
    private Call<List<User>> call;
    private Call<List<User>> callMoreUsers;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_git_hub_users, container, false);
        unbinder = ButterKnife.bind(this, view);

        adapter = new UserRecycleAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        createRetrofitInstance();
        call =  gitHubService.getUsers(0);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                adapter.addUsers(response.body());
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(getContext(), R.string.error_message, Toast.LENGTH_SHORT).show();
            }
        });

        adapter.setLoadMoreListener(userId -> {
            if(callMoreUsers != null && !callMoreUsers.isExecuted()){
                return;
            }
            callMoreUsers =  gitHubService.getUsers(0);
            callMoreUsers.enqueue(new Callback<List<User>>() {
                @Override
                public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                    adapter.addUsers(response.body());
                }

                @Override
                public void onFailure(Call<List<User>> call, Throwable t) {
                    Toast.makeText(getContext(), R.string.error_message, Toast.LENGTH_SHORT).show();
                }
            });
        });

        return view;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(call != null){
            call.cancel();
        }
        unbinder.unbind();
    }

    private void createRetrofitInstance(){
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        ChuckInterceptor chuckInterceptor = new ChuckInterceptor(getContext());
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY))
                .addNetworkInterceptor(chuckInterceptor)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        gitHubService = retrofit.create(GitHubService.class);
    }
}
