package me.bkkn.users.user;

import java.util.Objects;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user")
public final class User {
    @PrimaryKey(autoGenerate = true)
    public long userId;
    //    @ColumnInfo(name = "title")
    public String name;

    public String avatar;

    public int age;


    public User(String name, String avatar, long userId, int age) {
        this.name = name;
        this.avatar = avatar;
        this.userId = userId;
        this.age = age;
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
        User user = (User) o;
        return Objects.equals(name, user.name) &&
                Objects.equals(avatar, user.avatar);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, avatar);
    }
}
