package com.example.basiccvapli.viewmodels;

import android.app.Activity;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.basiccvapli.repository.Repository;

import java.util.Map;

public class DetailsViewModel extends ViewModel {

    public MutableLiveData<String> name = new MutableLiveData<>();
    public MutableLiveData<String> email = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();
    public MutableLiveData<String> location = new MutableLiveData<>();

    private Repository repository;

    public void init(Activity activity) {
        repository = new Repository(activity);
    }

    public void saveUserDetails(String useremail, Map<String, String> map) {
        repository.saveUserDetails(useremail, map);
    }

    public void saveDetails(Map<String, String> hashmap, FragmentActivity fragmentActivity, String password) {
        repository.saveDetails(hashmap, fragmentActivity, password);
    }
}
