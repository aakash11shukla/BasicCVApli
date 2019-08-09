package com.example.basiccvapli.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SignUpViewModel extends ViewModel {
    public MutableLiveData<String> phone_no = new MutableLiveData<>();
    public MutableLiveData<String> code = new MutableLiveData<>();
    public MutableLiveData<String> submit = new MutableLiveData<>();
    public MutableLiveData<Boolean> phone_visibilty = new MutableLiveData<>();
    public MutableLiveData<Boolean> submit_visibilty = new MutableLiveData<>();
    public MutableLiveData<Boolean> code_visibilty = new MutableLiveData<>();
    public MutableLiveData<Boolean> otp_visibilty = new MutableLiveData<>();

}
