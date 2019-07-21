package com.example.basiccvapli.firebaseUtils;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.basiccvapli.AuthFragment;
import com.example.basiccvapli.R;
import com.google.firebase.firestore.DocumentReference;

public class FirebaseUtil {

    public static DocumentReference databaseReference;
    private static FirebaseUtil firebaseUtil;

    public static void signIn(FragmentManager fragmentManager) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new AuthFragment());
        fragmentTransaction.commit();
    }
}
