package me.bkkn.users.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import me.bkkn.users.user.User;

@Database(entities = User.class, version = 3)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}
