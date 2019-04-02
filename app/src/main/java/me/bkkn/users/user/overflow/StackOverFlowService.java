package me.bkkn.users.user.overflow;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface StackOverFlowService {
    @GET("users")
    Single<OverflowUsersUserModel.UsersResponse> getUsers(@Query("page") int page, @Query("site")String site);
}
