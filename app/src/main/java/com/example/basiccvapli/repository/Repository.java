package com.example.basiccvapli.repository;

import android.app.Activity;
import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.basiccvapli.R;
import com.example.basiccvapli.firebaseUtils.FirebaseQueryLiveData;
import com.example.basiccvapli.firebaseUtils.FirebaseUtil;
import com.example.basiccvapli.models.Education;
import com.example.basiccvapli.models.Experience;
import com.example.basiccvapli.models.Internship;
import com.example.basiccvapli.models.PersonalDetails;
import com.example.basiccvapli.models.Skill;
import com.google.firebase.database.DataSnapshot;

public class Repository {

    private LiveData<DataSnapshot> dataSnapshot = null;
    private final FirebaseQueryLiveData liveData;
    private Activity activity;

    public Repository(Activity activity) {
        this.activity = activity;
        liveData = new FirebaseQueryLiveData(FirebaseUtil.databaseReference);
    }

    public LiveData<DataSnapshot> getData(){
        return liveData;
    }

    public void saveExperience(Experience experience) {
        FirebaseUtil.databaseReference.collection(activity.getString(R.string.experience)).add(experience);
    }

    public void saveSkill(Skill skill){
        FirebaseUtil.databaseReference.collection(activity.getString(R.string.skills)).add(skill);
    }

    public void saveEducation(Education education) {
        FirebaseUtil.databaseReference.collection(activity.getString(R.string.education)).add(education);
    }

    public void saveInternship(Internship internship) {
        FirebaseUtil.databaseReference.collection(activity.getString(R.string.Internships)).add(internship);
    }

    public void savePersonalDetails(PersonalDetails personalDetails) {
        FirebaseUtil.databaseReference.collection(activity.getString(R.string.personalDetails)).add(personalDetails);
    }

    public void updateExperience(Experience experience, String key) {
        FirebaseUtil.databaseReference.collection(activity.getString(R.string.experience)).document(key).set(experience);
    }

    public void updateSkill(Skill skill, String key) {
        FirebaseUtil.databaseReference.collection(activity.getString(R.string.skills)).document(key).set(skill);
    }

    public void updateInternship(Internship internship, String key) {
        FirebaseUtil.databaseReference.collection(activity.getString(R.string.Internships)).document(key).set(internship);
    }

    public void updateEducation(Education education, String key) {
        FirebaseUtil.databaseReference.collection(activity.getString(R.string.education)).document(key).set(key);
    }
}
