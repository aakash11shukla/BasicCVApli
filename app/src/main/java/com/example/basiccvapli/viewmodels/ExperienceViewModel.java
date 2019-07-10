package com.example.basiccvapli.viewmodels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.basiccvapli.models.Experience;
import com.example.basiccvapli.repository.ExperienceRepository;
import com.google.firebase.database.DataSnapshot;

public class ExperienceViewModel extends ViewModel {

    public MutableLiveData<String> companyName = new MutableLiveData<>();
    public MutableLiveData<String> industry = new MutableLiveData<>();
    public MutableLiveData<String> designation = new MutableLiveData<>();
    public MutableLiveData<String> location = new MutableLiveData<>();
    public MutableLiveData<String> from = new MutableLiveData<>();
    public MutableLiveData<String> to = new MutableLiveData<>();

    private ExperienceRepository repository;

    private Context context;

    public void init(Context context){
        this.context = context;
        repository = new ExperienceRepository(context);
    }
    public void save(Experience experience){
        repository.save(experience);
    }

    public LiveData<DataSnapshot> getDetails(){
        return repository.getData();
    }

}
