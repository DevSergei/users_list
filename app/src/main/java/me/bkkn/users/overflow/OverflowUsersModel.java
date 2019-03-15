package me.bkkn.users.overflow;

import java.util.ArrayList;
import java.util.List;

import me.bkkn.users.Model;
import me.bkkn.users.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OverflowUsersModel implements Model {

    private StackOverFlowService stackOverFlowService;

    public OverflowUsersModel(StackOverFlowService gitHubService) {
        this.stackOverFlowService = gitHubService;
    }

    @Override
    public void getUsers(ModelResponse<List<User>> modelResponse) {
        stackOverFlowService.getUsers(1, "stackoverflow")
                .enqueue(new Callback<UsersResponse>() {

                    @Override
                    public void onResponse(Call<UsersResponse> call, Response<UsersResponse> response) {
                        ArrayList<User> users = new ArrayList<>();
                        for (StackOverflowUser overflowUser : response.body().getItems()) {
                            users.add(overflowUser.mapToUser());
                        }
                    }

                    @Override
                    public void onFailure(Call<UsersResponse> call, Throwable t) {
                        modelResponse.onError(t);
                    }
                });
    }

    public final class UsersResponse {
        private ArrayList<StackOverflowUser> items;

        public UsersResponse(ArrayList<StackOverflowUser> items) {
            this.items = items;
        }

        public ArrayList<StackOverflowUser> getItems() {
            return items;
        }
    }

}
