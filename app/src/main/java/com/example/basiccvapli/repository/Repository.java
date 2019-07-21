package com.example.basiccvapli.repository;

import android.app.Activity;
import android.preference.PreferenceManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;

import com.example.basiccvapli.BasiccvFragment;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class Repository {

    private LiveData<DataSnapshot> dataSnapshot = null;
    private final FirebaseQueryLiveData liveData;
    private Activity activity;

    public Repository(Activity activity) {
        this.activity = activity;
        liveData = new FirebaseQueryLiveData(FirebaseUtil.databaseReference);
    }

    public LiveData<DataSnapshot> getData() {
        return liveData;
    }

    public void saveExperience(Experience experience) {
        FirebaseUtil.databaseReference.collection(activity.getString(R.string.experience)).add(experience);
    }

    public void saveSkill(Skill skill) {
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

    public void saveUserDetails(String useremail, Map<String, String> map) {
        FirebaseFirestore.getInstance().collection("users").document(useremail).set(map);
    }

    public void saveDetails(final Map<String, String> hashmap, final FragmentActivity fragmentActivity) {
        FirebaseUtil.databaseReference.set(hashmap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            PreferenceManager.getDefaultSharedPreferences(activity).edit().putBoolean(activity.getString(R.string.detailsCompletedKey), true).apply();
                            Toast.makeText(activity, "Welcome " + hashmap.get("name"), Toast.LENGTH_SHORT).show();
                            fragmentActivity.getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_container, new BasiccvFragment())
                                    .commit();
                        } else {
                            Toast.makeText(activity, "Failed. Please Retry", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
