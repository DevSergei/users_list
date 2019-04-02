package me.bkkn.users.user.github;

import com.google.gson.annotations.SerializedName;

import me.bkkn.users.user.User;

public class GitHubUser {
    @SerializedName("login") String name;
    @SerializedName("avatar_url") String avatar;
    @SerializedName("id") long userId;

    public User mapToUser(){
        return new User(name, avatar, userId,0);
    }
}
