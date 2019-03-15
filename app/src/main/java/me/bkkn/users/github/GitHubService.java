package me.bkkn.users.github;

import java.util.List;

import me.bkkn.users.github.GitHubUser;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GitHubService {
    @GET("users")
    Call<List<GitHubUser>> getUsers(@Query("since") long id);
}
