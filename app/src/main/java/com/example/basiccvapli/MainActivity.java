package com.example.basiccvapli;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        CommonApplication.setFragmentManager(getSupportFragmentManager());
        if (savedInstanceState == null) {
            if (!PreferenceManager.getDefaultSharedPreferences(this).contains(getString(R.string.detailsCompletedKey)) || !PreferenceManager.getDefaultSharedPreferences(this).getBoolean(getString(R.string.detailsCompletedKey), false)) {
                CommonApplication.getFragmentManager().beginTransaction()
                        .add(R.id.fragment_container, new DetailsFragment())
                        .commit();
            } else {
                CommonApplication.getFragmentManager().beginTransaction()
                        .add(R.id.fragment_container, new BasiccvFragment())
                        .commit();
            }
        }

//        firebaseAuth = FirebaseAuth.getInstance();
//        authStateListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                if (firebaseAuth.getCurrentUser() == null) {
//                    FirebaseUtil.signIn(getSupportFragmentManager());
//                }
//            }
//        };
//        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.sign_out) {
            FirebaseAuth.getInstance().signOut();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (fragment instanceof SignUpFragment) {
            if (fragment.getView().findViewById(R.id.otp).getVisibility() == View.VISIBLE) {
                fragment.getView().findViewById(R.id.code).setVisibility(View.GONE);
                fragment.getView().findViewById(R.id.otp).setVisibility(View.GONE);
                fragment.getView().findViewById(R.id.phone_number).setVisibility(View.VISIBLE);
                ((Button) fragment.getView().findViewById(R.id.submit)).setText(getString(R.string.submit));
                return;
            }
        } else if (fragment instanceof AuthFragment || fragment instanceof BasiccvFragment) {
            finish();
        }
        super.onBackPressed();
    }
}