package com.example.basiccvapli.firebaseUtils;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.basiccvapli.AuthFragment;
import com.example.basiccvapli.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseUtil {

    private FirebaseUtil firebaseUtil;
    private FirebaseFirestore firestoreinstance = FirebaseFirestore.getInstance();
    public static DocumentReference databaseReference;

    public static void signIn(FragmentManager fragmentManager) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new AuthFragment());
        fragmentTransaction.commit();
    }

    public FirebaseUtil getFirebaseUtil() {
        if(firebaseUtil == null){
            firebaseUtil = new FirebaseUtil();
        }
        return firebaseUtil;
    }

    public FirebaseFirestore getFirestoreinstance() {
        return firebaseUtil.firestoreinstance;
    }
}
