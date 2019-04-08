package me.bkkn.users.di;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import dagger.Module;
import dagger.Provides;
import me.bkkn.users.db.AppDatabase;


@Module
public class DatabaseModule {
    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE USER ADD COLUMN age INTEGER DEFAULT 0");
        }
    };
    @Provides
    @PerApplication
    AppDatabase provideDatabase(Context context){
        return Room.databaseBuilder(context, AppDatabase.class, "MyDatabase")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .addMigrations(MIGRATION_1_2)
                .build();
    }
}
