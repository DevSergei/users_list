package me.bkkn.users.db;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import me.bkkn.users.users.User;

@Dao
public interface UserDao {
    @Query("SELECT * from user")
    List<User> getAllUsers();

    @Insert
    void insert(User user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);
}