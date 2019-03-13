package me.bkkn.users.overflow;


import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public final class UserOverFlow {
    @SerializedName("display_name") private String name;
    @SerializedName("profile_image") private String avatar;
    @SerializedName("accaunt_id") private long userId;

    public UserOverFlow(String name, String avatar, long userId) {
        this.name = name;
        this.avatar = avatar;
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public String getAvatar() {
        return avatar;
    }

    public long getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserOverFlow user = (UserOverFlow) o;
        return Objects.equals(name, user.name) &&
                Objects.equals(avatar, user.avatar);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, avatar);
    }
}
