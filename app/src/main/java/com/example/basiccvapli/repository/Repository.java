package com.example.basiccvapli.repository;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;

import com.example.basiccvapli.BasiccvFragment;
import com.example.basiccvapli.CommonApplication;
import com.example.basiccvapli.R;
import com.example.basiccvapli.firebaseUtils.FirebaseQueryLiveData;
import com.example.basiccvapli.firebaseUtils.FirebaseUtil;
import com.example.basiccvapli.models.Education;
import com.example.basiccvapli.models.Experience;
import com.example.basiccvapli.models.Internship;
import com.example.basiccvapli.models.PersonalDetails;
import com.example.basiccvapli.models.Skill;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class Repository {

    private static FirebaseQueryLiveData liveData;

    public Repository() {
        if (FirebaseUtil.databaseReference != null && liveData != null) {
            liveData = new FirebaseQueryLiveData(FirebaseUtil.databaseReference);
        }
    }

    public LiveData<DocumentSnapshot> getData() {
        return liveData;
    }

    public void saveExperience(Experience experience) {
        FirebaseUtil.databaseReference.collection(CommonApplication.getCommonApplication().getApplicationContext().getString(R.string.experience)).add(experience);
    }

    public void saveSkill(Skill skill) {
        FirebaseUtil.databaseReference.collection(CommonApplication.getCommonApplication().getApplicationContext().getString(R.string.skills)).add(skill);
    }

    public void saveEducation(Education education) {
        FirebaseUtil.databaseReference.collection(CommonApplication.getCommonApplication().getApplicationContext().getString(R.string.education)).add(education);
    }

    public void saveInternship(Internship internship) {
        FirebaseUtil.databaseReference.collection(CommonApplication.getCommonApplication().getApplicationContext().getString(R.string.Internships)).add(internship);
    }

    public void savePersonalDetails(PersonalDetails personalDetails) {
        FirebaseUtil.databaseReference.collection(CommonApplication.getCommonApplication().getApplicationContext().getString(R.string.personalDetails)).add(personalDetails);
    }

    public void updateExperience(Experience experience, String key) {
        FirebaseUtil.databaseReference.collection(CommonApplication.getCommonApplication().getApplicationContext().getString(R.string.experience)).document(key).set(experience);
    }

    public void updateSkill(Skill skill, String key) {
        FirebaseUtil.databaseReference.collection(CommonApplication.getCommonApplication().getApplicationContext().getString(R.string.skills)).document(key).set(skill);
    }

    public void updateInternship(Internship internship, String key) {
        FirebaseUtil.databaseReference.collection(CommonApplication.getCommonApplication().getApplicationContext().getString(R.string.Internships)).document(key).set(internship);
    }

    public void updateEducation(Education education, String key) {
        FirebaseUtil.databaseReference.collection(CommonApplication.getCommonApplication().getApplicationContext().getString(R.string.education)).document(key).set(key);
    }

    public void saveUserDetails(String useremail, Map<String, String> map) {
        FirebaseFirestore.getInstance().collection("users").document(useremail).set(map);
    }

    public void saveDetails(final Map<String, String> hashmap, final FragmentActivity fragmentActivity, final String password) {
        FirebaseUtil.databaseReference.set(hashmap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            CommonApplication.getCommonApplication().getEditor().putBoolean(CommonApplication.getCommonApplication().getApplicationContext().getString(R.string.detailsCompletedKey), true).apply();
                            Toast.makeText(CommonApplication.getCommonApplication().getApplicationContext(), "Welcome " + hashmap.get("name"), Toast.LENGTH_SHORT).show();
                            CommonApplication.getFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_container, new BasiccvFragment())
                                    .commit();
                        } else {
                            Toast.makeText(CommonApplication.getCommonApplication().getApplicationContext(), "Failed. Please Retry", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
