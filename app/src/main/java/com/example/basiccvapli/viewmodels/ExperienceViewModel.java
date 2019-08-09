package com.example.basiccvapli.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.basiccvapli.models.Experience;
import com.example.basiccvapli.repository.Repository;
import com.google.firebase.firestore.DocumentSnapshot;

public class ExperienceViewModel extends ViewModel {

    public MutableLiveData<String> companyName = new MutableLiveData<>();
    public MutableLiveData<String> industry = new MutableLiveData<>();
    public MutableLiveData<String> designation = new MutableLiveData<>();
    public MutableLiveData<String> location = new MutableLiveData<>();
    public MutableLiveData<String> from = new MutableLiveData<>();
    public MutableLiveData<String> to = new MutableLiveData<>();
    public MutableLiveData<String> update = new MutableLiveData<>();

    private Repository repository;

    public void init() {
        repository = new Repository();
    }

    public void save(Experience experience) {
        repository.saveExperience(experience);
    }

    public LiveData<DocumentSnapshot> getDetails() {
        return repository.getData();
    }

    public void update(Experience experience, String key) {
        repository.updateExperience(experience, key);
    }
}
