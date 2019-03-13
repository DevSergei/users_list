package me.bkkn.users.overflow;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface StackOverFlowService {
    @GET("users")
    Call<UsersResponse> getUsers(@Query("page") int page, @Query("site")String site);
}
