package me.bkkn.users.user.github;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GitHubService {
    @GET("users")
    Single<List<GitHubUser>> getUsers(@Query("since") long id);
}
