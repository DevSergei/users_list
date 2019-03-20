package me.bkkn.users.users.overflow;

import com.google.gson.annotations.SerializedName;

import me.bkkn.users.users.User;

public class StackOverflowUser {
    @SerializedName("display_name") String name;
    @SerializedName("profile_image") String avatar;
    @SerializedName("account_id") long userId;

    public User mapToUser(){
        return new User(name, avatar, userId);
    }
}
