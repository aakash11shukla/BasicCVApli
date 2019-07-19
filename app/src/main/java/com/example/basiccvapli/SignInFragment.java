package com.example.basiccvapli;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SignInFragment extends Fragment implements View.OnClickListener{

    private Button signin;
    private Button login;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        signin = getView().findViewById(R.id.signin);
        login = getView().findViewById(R.id.login);

        signin.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        if(id == R.id.signin){
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new PhoneAuthenticationFragment())
                    .addToBackStack(PhoneAuthenticationFragment.class.getSimpleName())
                    .commit();
        }

    }
}
