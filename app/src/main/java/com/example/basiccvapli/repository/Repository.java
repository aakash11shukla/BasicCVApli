package com.example.basiccvapli.repository;

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
    private Context context;

    public Repository(Context context) {
        this.context = context;
        liveData = new FirebaseQueryLiveData(FirebaseUtil.databaseReference);
    }

    public LiveData<DataSnapshot> getData(){
        return liveData;
    }

    public void saveExperience(Experience experience) {
        FirebaseUtil.databaseReference.child(context.getString(R.string.experience)).push().setValue(experience);
    }

    public void saveSkill(Skill skill){
        FirebaseUtil.databaseReference.child(context.getString(R.string.skills)).push().setValue(skill);
    }

    public void saveEducation(Education education) {
        FirebaseUtil.databaseReference.child(context.getString(R.string.education)).push().setValue(education);
    }

    public void saveInternship(Internship internship) {
        FirebaseUtil.databaseReference.child(context.getString(R.string.Internships)).push().setValue(internship);
    }

    public void savePersonalDetails(PersonalDetails personalDetails) {
        FirebaseUtil.databaseReference.child(context.getString(R.string.personalDetails)).setValue(personalDetails);
    }

    public void updateExperience(Experience experience, String key) {
        FirebaseUtil.databaseReference.child(context.getString(R.string.experience)).child(key).setValue(experience);
    }

    public void updateSkill(Skill skill, String key) {
        FirebaseUtil.databaseReference.child(context.getString(R.string.skills)).child(key).setValue(skill);
    }

    public void updateInternship(Internship internship, String key) {
        FirebaseUtil.databaseReference.child(context.getString(R.string.Internships)).child(key).setValue(internship);
    }

    public void updateEducation(Education education, String key) {
        FirebaseUtil.databaseReference.child(context.getString(R.string.education)).child(key).setValue(education);
    }
}
