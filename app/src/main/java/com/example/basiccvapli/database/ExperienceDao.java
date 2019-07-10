package com.example.basiccvapli.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.basiccvapli.models.Experience;

import java.util.List;

@Dao
public interface ExperienceDao {

    @Insert
    void insert(Experience experience);

    @Update
    void update(Experience experience);

    @Delete
    void delete(Experience experience);

    @Query("SELECT * from experience_table")
    LiveData<List<Experience>> getAllExperiences();
}
