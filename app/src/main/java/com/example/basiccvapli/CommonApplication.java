package com.example.basiccvapli;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.example.basiccvapli.firebaseUtils.FirebaseUtil;
import com.google.firebase.auth.FirebaseAuth;

public class CommonApplication extends Application {

    private static CommonApplication commonApplication;

    private static FirebaseAuth firebaseAuth;
    private static FirebaseAuth.AuthStateListener authStateListener;
    private static FragmentManager fragmentManager;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public static CommonApplication getCommonApplication() {
        return commonApplication;
    }

    public static FirebaseAuth getFirebaseAuth() {
        return firebaseAuth;
    }

    public static FirebaseAuth.AuthStateListener getAuthStateListener() {
        return authStateListener;
    }

    public static FragmentManager getFragmentManager() {
        return fragmentManager;
    }

    public static void setFragmentManager(FragmentManager fragmentManager) {

        CommonApplication.fragmentManager = fragmentManager;
        setAuthStateListener();
    }

    private static void setAuthStateListener() {
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    FirebaseUtil.signIn(fragmentManager);
                }
            }
        };
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public SharedPreferences.Editor getEditor() {
        return editor;
    }

    @Override
    public Context getApplicationContext() {
        return commonApplication.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.useAppLanguage();
        commonApplication = new CommonApplication();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //editor = sharedPreferences.edit();
    }

    @Override
    public void unregisterActivityLifecycleCallbacks(ActivityLifecycleCallbacks callback) {
        super.unregisterActivityLifecycleCallbacks(callback);
        firebaseAuth.removeAuthStateListener(authStateListener);
    }
}
