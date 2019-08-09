package com.example.basiccvapli;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.basiccvapli.firebaseUtils.FirebaseUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;


public class BasiccvFragment extends Fragment {

    private FragmentManager fragmentManager;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_basiccv, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FirebaseUtil.databaseReference = FirebaseFirestore.getInstance().collection("candidates").document(FirebaseAuth.getInstance().getCurrentUser().getEmail());
    }

    public class BasiccvHandler {

        public void onClickExperience() {
            ListFragment fragment = new ListFragment();
            Bundle bundle = new Bundle();
            bundle.putString("list", getString(R.string.experience));
            fragment.setArguments(bundle);
            CommonApplication.getFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(ListFragment.class.getSimpleName())
                    .commit();
        }

        public void onClickInternship() {
            ListFragment fragment = new ListFragment();
            Bundle bundle = new Bundle();
            bundle.putString("list", getString(R.string.Internships));
            fragment.setArguments(bundle);
            CommonApplication.getFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(ListFragment.class.getSimpleName())
                    .commit();
        }

        public void onClickEducation() {
            ListFragment fragment = new ListFragment();
            Bundle bundle = new Bundle();
            bundle.putString("list", getString(R.string.education));
            fragment.setArguments(bundle);
            CommonApplication.getFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(ListFragment.class.getSimpleName())
                    .commit();
        }

        public void onClickSkill() {
            ListFragment fragment = new ListFragment();
            Bundle bundle = new Bundle();
            bundle.putString("list", getString(R.string.skills));
            fragment.setArguments(bundle);
            CommonApplication.getFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(ListFragment.class.getSimpleName())
                    .commit();
        }

        public void onClickPersonalDetail() {
            PersonalDetailsFragment personalDetailsFragment = new PersonalDetailsFragment();
            CommonApplication.getFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, personalDetailsFragment)
                    .addToBackStack(PersonalDetailsFragment.class.getSimpleName())
                    .commit();
        }

    }
}
