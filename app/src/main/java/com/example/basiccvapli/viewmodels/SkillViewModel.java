package com.example.basiccvapli.viewmodels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.basiccvapli.models.Skill;
import com.example.basiccvapli.repository.Repository;
import com.google.firebase.database.DataSnapshot;

public class SkillViewModel extends ViewModel {

    public MutableLiveData<String> skillName = new MutableLiveData<>();

    private Repository repository;

    public void init(Context context){
        repository = new Repository(context);
    }
    public void save(Skill skill){
        repository.saveSkill(skill);
    }

    public LiveData<DataSnapshot> getDetails(){
        return repository.getData();
    }

    public void update(Skill skill, String key) {
        repository.updateSkill(skill, key);
    }
}
