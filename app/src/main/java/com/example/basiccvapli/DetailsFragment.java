package com.example.basiccvapli;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.basiccvapli.firebaseUtils.FirebaseUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class DetailsFragment extends Fragment {

    private EditText fullName;
    private EditText email;
    private EditText password;
    private EditText location;
    private Button submit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fullName = getView().findViewById(R.id.full_name);
        email = getView().findViewById(R.id.email);
        password = getView().findViewById(R.id.password);
        location = getView().findViewById(R.id.location);
        submit = getView().findViewById(R.id.details);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = fullName.getText().toString();
                String useremail = email.getText().toString();
                String userpasswd = password.getText().toString();
                String userloc = location.getText().toString();
                if(username.isEmpty() || useremail.isEmpty() || userpasswd.isEmpty() || userloc.isEmpty()){
                    Toast.makeText(getContext(), "Fields cannot be empty.", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    validate(username, useremail, userpasswd, userloc);
                }
            }
        });
    }

    private void validate(String username, String useremail, String userpasswd, String userloc) {
        if(!Patterns.EMAIL_ADDRESS.matcher(useremail).matches()){
            email.setError("Invalid email id");
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("Full Name", username);
        map.put("Email Id", useremail);
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(userpasswd.getBytes(StandardCharsets.UTF_8));
            map.put("Password", String.format( "%064x", new BigInteger( 1, messageDigest.digest() )));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        map.put("Location", userloc);
        FirebaseUtil.databaseReference = FirebaseFirestore.getInstance().collection("candidates").document(useremail);
        FirebaseUtil.databaseReference.set(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putBoolean(getString(R.string.detailsCompletedKey), true).apply();
                            Toast.makeText(getContext(), "Welcome.", Toast.LENGTH_SHORT).show();
                            getActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.fragment_container, new BasiccvFragment())
                                    .commit();
                        }
                    }
                });
    }
}
