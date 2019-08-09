package com.example.basiccvapli.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.basiccvapli.models.Education;
import com.example.basiccvapli.repository.Repository;
import com.google.firebase.firestore.DocumentSnapshot;

public class EducationViewModel extends ViewModel {

    public MutableLiveData<String> instituteName = new MutableLiveData<>();
    public MutableLiveData<String> marks = new MutableLiveData<>();
    public MutableLiveData<String> fieldOfStudy = new MutableLiveData<>();
    public MutableLiveData<String> from = new MutableLiveData<>();
    public MutableLiveData<String> to = new MutableLiveData<>();
    public MutableLiveData<String> update = new MutableLiveData<>();

    private Repository repository;

    public void init() {
        repository = new Repository();
    }

    public void save(Education education) {
        repository.saveEducation(education);
    }

    public LiveData<DocumentSnapshot> getDetails() {
        return repository.getData();
    }

    public void update(Education education, String key) {
        repository.updateEducation(education, key);
    }
}
