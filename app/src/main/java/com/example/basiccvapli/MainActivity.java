package com.example.basiccvapli;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.basiccvapli.firebaseUtils.FirebaseUtil;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        FirebaseUtil.getInstance().openFbReference(getSupportFragmentManager());
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (savedInstanceState == null) {
            if(!PreferenceManager.getDefaultSharedPreferences(this).getBoolean(getString(R.string.detailsCompletedKey), false)){
                fragmentManager.beginTransaction()
                        .add(R.id.fragment_container, new DetailsFragment())
                        .commit();
            }else{
                fragmentManager.beginTransaction()
                        .add(R.id.fragment_container, new BasiccvFragment())
                        .commit();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.sign_out){
            FirebaseAuth.getInstance().signOut();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        FirebaseUtil.detachListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseUtil.attachListener();
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if(fragment instanceof  PhoneAuthenticationFragment){
            if(fragment.getView().findViewById(R.id.otp).getVisibility() == View.VISIBLE){
                fragment.getView().findViewById(R.id.code).setVisibility(View.GONE);
                fragment.getView().findViewById(R.id.otp).setVisibility(View.GONE);
                fragment.getView().findViewById(R.id.phone_number).setVisibility(View.VISIBLE);
                ((Button)fragment.getView().findViewById(R.id.submit)).setText(getString(R.string.submit));
                return;
            }
        }else if(fragment instanceof DetailsFragment){
            PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean(getString(R.string.detailsCompletedKey), false).apply();
        }else if(fragment instanceof SignInFragment){
            finish();
        }
        super.onBackPressed();

    }
}
