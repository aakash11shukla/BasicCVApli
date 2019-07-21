package com.example.basiccvapli.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SignUpViewModel extends ViewModel {

    public MutableLiveData<String> phone_no = new MutableLiveData<>();
    public MutableLiveData<String> code = new MutableLiveData<>();

}
