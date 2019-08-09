package com.example.basiccvapli.firebaseUtils;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

import javax.annotation.Nullable;

public class FirebaseQueryLiveData extends LiveData<DocumentSnapshot> {
    private static final String LOG_TAG = "FirebaseQueryLiveData";

    private final DocumentReference query;
    private ListenerRegistration registration;
    private MyEventListener listener = new MyEventListener();

    public FirebaseQueryLiveData(DocumentReference ref) {
        this.query = ref;
    }

    private class MyEventListener implements EventListener<DocumentSnapshot> {

        private static final String TAG = "FirestoreException";

        @Override
        public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
            if (e != null) {
                Log.w(TAG, "listen:error", e);
                return;
            }

            if (getValue() == null) {
                setValue(documentSnapshot);
            } else {
                setValue(null);
            }
        }
    }

    @Override
    protected void onActive() {
        Log.d(LOG_TAG, "onActive");
        registration = query.addSnapshotListener(listener);
    }

    @Override
    protected void onInactive() {
        Log.d(LOG_TAG, "onInactive");
        registration.remove();
    }
}