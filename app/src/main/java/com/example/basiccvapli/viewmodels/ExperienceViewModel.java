package com.example.basiccvapli.viewmodels;

import android.app.Activity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.basiccvapli.models.Experience;
import com.example.basiccvapli.repository.Repository;
import com.google.firebase.database.DataSnapshot;

public class ExperienceViewModel extends ViewModel {

    public MutableLiveData<String> companyName = new MutableLiveData<>();
    public MutableLiveData<String> industry = new MutableLiveData<>();
    public MutableLiveData<String> designation = new MutableLiveData<>();
    public MutableLiveData<String> location = new MutableLiveData<>();
    public MutableLiveData<String> from = new MutableLiveData<>();
    public MutableLiveData<String> to = new MutableLiveData<>();

    private Repository repository;

    public void init(Activity activity) {
        repository = new Repository(activity);
    }

    public void save(Experience experience) {
        repository.saveExperience(experience);
    }

    public LiveData<DataSnapshot> getDetails() {
        return repository.getData();
    }

    public void update(Experience experience, String key) {
        repository.updateExperience(experience, key);
    }
}
