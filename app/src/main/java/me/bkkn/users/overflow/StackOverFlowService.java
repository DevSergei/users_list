package me.bkkn.users.overflow;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface StackOverFlowService {
    @GET("users")
    Call<OverflowUsersModel.UsersResponse> getUsers(@Query("page") int page, @Query("site")String site);
}
