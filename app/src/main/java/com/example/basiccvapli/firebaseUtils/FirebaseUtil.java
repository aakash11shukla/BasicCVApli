package com.example.basiccvapli.firebaseUtils;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.basiccvapli.R;
import com.example.basiccvapli.SignInFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseUtil {

    private FirebaseFirestore firebaseDatabase;
    public static DocumentReference databaseReference;
    private static FirebaseAuth firebaseAuth;
    private static FirebaseAuth.AuthStateListener authStateListener;
    private static FirebaseUtil firebaseUtil;
    private FragmentManager fragmentManager;

    public static synchronized FirebaseUtil getInstance() {
        if(firebaseUtil == null){
            firebaseUtil = new FirebaseUtil();
        }
        return firebaseUtil;
    }

    public void openFbReference(FragmentManager fragmentManager) {
        firebaseDatabase = FirebaseFirestore.getInstance();
        this.fragmentManager = fragmentManager;
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    signIn();
                } else {
                    String email = firebaseAuth.getCurrentUser().getEmail();
                    if (email != null) {
                        databaseReference = firebaseDatabase.collection("candidates").document(email);
                    }
                }
            }
        };
    }

    private void signIn() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new SignInFragment());
        fragmentTransaction.addToBackStack(SignInFragment.class.getSimpleName());
        fragmentTransaction.commit();
    }

    public static void attachListener() {
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    public static void detachListener() {
        firebaseAuth.removeAuthStateListener(authStateListener);
    }

}
