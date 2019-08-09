package com.example.basiccvapli.viewmodels;

import android.app.Activity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.basiccvapli.models.PersonalDetails;
import com.example.basiccvapli.repository.Repository;
import com.google.firebase.firestore.DocumentSnapshot;

public class PersonalDetailsViewModel extends ViewModel {

    public MutableLiveData<String> name = new MutableLiveData<>();
    public MutableLiveData<String> gender = new MutableLiveData<>();
    public MutableLiveData<String> dob = new MutableLiveData<>();
    public MutableLiveData<String> aadharNo = new MutableLiveData<>();
    public MutableLiveData<String> email = new MutableLiveData<>();
    public MutableLiveData<String> homeTown = new MutableLiveData<>();
    public MutableLiveData<String> pinCode = new MutableLiveData<>();
    public MutableLiveData<String> permanentAddress = new MutableLiveData<>();
    public MutableLiveData<String> filepath = new MutableLiveData<>();
    public MutableLiveData<String> maritalStatus = new MutableLiveData<>();

    private Repository repository;

    public void init() {
        repository = new Repository();
    }

    public LiveData<DocumentSnapshot> getDetails() {
        return repository.getData();
    }

    public void save(PersonalDetails personalDetails) {
        repository.savePersonalDetails(personalDetails);
    }
}
