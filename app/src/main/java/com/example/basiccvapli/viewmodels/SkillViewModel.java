package com.example.basiccvapli.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.basiccvapli.models.Skill;
import com.example.basiccvapli.repository.Repository;
import com.google.firebase.firestore.DocumentSnapshot;

public class SkillViewModel extends ViewModel {

    public MutableLiveData<String> skillName = new MutableLiveData<>();

    private Repository repository;

    public void init() {
        repository = new Repository();
    }

    public void save(Skill skill) {
        repository.saveSkill(skill);
    }

    public LiveData<DocumentSnapshot> getDetails() {
        return repository.getData();
    }

    public void update(Skill skill, String key) {
        repository.updateSkill(skill, key);
    }
}
