package me.bkkn.users.overflow;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

import me.bkkn.users.User;

public class StackOverflowUser {
    @SerializedName("display_name") String name;
    @SerializedName("profile_image") String avatar;
    @SerializedName("accaunt_id") long userId;

    public User mapToUser(){
        return new User(name, avatar, userId);
    }
}
