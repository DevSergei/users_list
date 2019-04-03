package me.bkkn.users.db;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import io.reactivex.Single;
import me.bkkn.users.user.User;

@Dao
public interface UserDao {
    @Query("SELECT * from user")
    Single<List<User>> getAllUsers();

    @Insert
    void insert(User user);
    @Insert
    void insertMany(List<User> user);

    @Update
    void update(User user);

    @Delete
    void deleteMany(List<User> user);
}
