package com.example.basiccvapli.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.basiccvapli.models.Experience;

@Database(entities = {Experience.class}, version = 1, exportSchema = false)
public abstract class ExperienceDatabase extends RoomDatabase {

    public abstract ExperienceDao experienceDao();
    private static volatile ExperienceDatabase INSTANCE;

    public static ExperienceDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ExperienceDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context,
                            ExperienceDatabase.class, "experience_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
