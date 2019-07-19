package com.example.basiccvapli.firebaseUtils;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;

public class FirebaseQueryLiveData extends LiveData<DataSnapshot> {
    private static final String LOG_TAG = "FirebaseQueryLiveData";

    private final DocumentReference query;

    public FirebaseQueryLiveData(DocumentReference ref) {
        this.query = ref;
    }
//
//    @Override
//    protected void onActive() {
//        Log.d(LOG_TAG, "onActive");
//        query.addSnapshotListener(listener);
//    }
//
//    @Override
//    protected void onInactive() {
//        Log.d(LOG_TAG, "onInactive");
//        query.removeEventListener(listener);
//    }
//
//    private class MyValueEventListener implements ValueEventListener {
//        @Override
//        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//            if(getValue() == null)
//                setValue(dataSnapshot);
//            else{
//                setValue(null);
//            }
//        }
//
//        @Override
//        public void onCancelled(@NonNull DatabaseError databaseError) {
//            Log.e(LOG_TAG, "Can't listen to query " + query, databaseError.toException());
//        }
//    }
}