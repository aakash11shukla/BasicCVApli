package com.example.basiccvapli.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.basiccvapli.database.ExperienceDao;
import com.example.basiccvapli.database.ExperienceDatabase;
import com.example.basiccvapli.firebaseUtils.FirebaseQueryLiveData;
import com.example.basiccvapli.firebaseUtils.FirebaseUtil;
import com.example.basiccvapli.models.Experience;
import com.google.firebase.database.DataSnapshot;

public class ExperienceRepository {

    private LiveData<DataSnapshot> dataSnapshot = null;
    private ExperienceDao experienceDao;
    private final FirebaseQueryLiveData liveData;

    public ExperienceRepository(Context context) {
        ExperienceDatabase db = ExperienceDatabase.getDatabase(context);
        experienceDao = db.experienceDao();
        liveData = new FirebaseQueryLiveData(FirebaseUtil.databaseReference);
    }

    public LiveData<DataSnapshot> getData(){
        return liveData;
    }

    public void save(Experience experience) {
        FirebaseUtil.databaseReference.child("Experience").push().setValue(experience);
    }
}
