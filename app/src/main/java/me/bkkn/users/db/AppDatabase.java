package me.bkkn.users.db;

import androidx.room.Database;
import androidx.room.Entity;
import androidx.room.RoomDatabase;
import me.bkkn.users.users.User;

@Database(entities = User.class, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}
