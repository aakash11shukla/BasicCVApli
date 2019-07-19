package com.example.basiccvapli.viewmodels;

import android.app.Activity;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.basiccvapli.models.Education;
import com.example.basiccvapli.repository.Repository;
import com.google.firebase.database.DataSnapshot;

public class EducationViewModel extends ViewModel {

    public MutableLiveData<String> instituteName = new MutableLiveData<>();
    public MutableLiveData<String> marks = new MutableLiveData<>();
    public MutableLiveData<String> fieldOfStudy = new MutableLiveData<>();
    public MutableLiveData<String> from = new MutableLiveData<>();
    public MutableLiveData<String> to = new MutableLiveData<>();

    private Repository repository;

    public void init(Activity activity){
        repository = new Repository(activity);
    }

    public void save(Education education){
        repository.saveEducation(education);
    }

    public LiveData<DataSnapshot> getDetails(){
        return repository.getData();
    }

    public void update(Education education, String key) {
        repository.updateEducation(education, key);
    }
}
