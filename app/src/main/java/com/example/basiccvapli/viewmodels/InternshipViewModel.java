package com.example.basiccvapli.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.basiccvapli.models.Internship;
import com.example.basiccvapli.repository.Repository;
import com.google.firebase.firestore.DocumentSnapshot;

public class InternshipViewModel extends ViewModel {

    public MutableLiveData<String> companyName = new MutableLiveData<>();
    public MutableLiveData<String> industry = new MutableLiveData<>();
    public MutableLiveData<String> designation = new MutableLiveData<>();
    public MutableLiveData<String> location = new MutableLiveData<>();
    public MutableLiveData<String> from = new MutableLiveData<>();
    public MutableLiveData<String> to = new MutableLiveData<>();

    private Repository repository;

    public void init() {
        repository = new Repository();
    }

    public void save(Internship internship) {
        repository.saveInternship(internship);
    }

    public LiveData<DocumentSnapshot> getDetails() {
        return repository.getData();
    }

    public void update(Internship internship, String key) {
        repository.updateInternship(internship, key);
    }
}
