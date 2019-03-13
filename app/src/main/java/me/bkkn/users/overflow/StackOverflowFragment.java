package me.bkkn.users.overflow;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.readystatesoftware.chuck.ChuckInterceptor;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.bkkn.users.GitHubService;
import me.bkkn.users.R;
import me.bkkn.users.overflow.UserOverFlow;
import me.bkkn.users.UserRecycleAdapter;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StackOverflowFragment extends Fragment {

    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    private Unbinder unbinder;
    private OverflowUserRecycleAdapter adapter;
    private StackOverFlowService stackOverFlowService;
    private Call<UsersResponse> call;
    private Call<UsersResponse> callMoreUsers;
    private int page = 1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_git_hub_users, container, false);
        unbinder = ButterKnife.bind(this, view);

        adapter = new OverflowUserRecycleAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        createRetrofitInstance();
        call =  stackOverFlowService.getUsers(1,"stackoverflow");
        call.enqueue(new Callback<UsersResponse>() {
            @Override
            public void onResponse(Call<UsersResponse> call, Response<UsersResponse> response) {
                adapter.addUsers(response.body().getItems());
            }

            @Override
            public void onFailure(Call<UsersResponse> call, Throwable t) {
                Toast.makeText(getContext(), R.string.error_message, Toast.LENGTH_SHORT).show();
            }
        });

        adapter.setLoadMoreListener(userId -> {
            if(callMoreUsers != null && !callMoreUsers.isExecuted()){
                return;
            }
            callMoreUsers =  stackOverFlowService.getUsers(page + 1,"stackoverflow");
            callMoreUsers.enqueue(new Callback<UsersResponse>() {
                @Override
                public void onResponse(Call<UsersResponse> call, Response<UsersResponse> response) {
                    adapter.addUsers(response.body().getItems());
                    page++;
                }

                @Override
                public void onFailure(Call<UsersResponse> call, Throwable t) {
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
                .baseUrl("https://api.stackexchange.com/2.2/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        stackOverFlowService = retrofit.create(StackOverFlowService.class);
    }
}
