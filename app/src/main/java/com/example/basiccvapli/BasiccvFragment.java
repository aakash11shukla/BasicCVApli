package com.example.basiccvapli;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.Objects;


public class BasiccvFragment extends Fragment implements View.OnClickListener {

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
        if (getView() != null) {
            getView().findViewById(R.id.experience_button).setOnClickListener(this);
            getView().findViewById(R.id.skills_button).setOnClickListener(this);
            getView().findViewById(R.id.education_button).setOnClickListener(this);
            getView().findViewById(R.id.personal_button).setOnClickListener(this);
            getView().findViewById(R.id.internship_button).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        ListFragment fragment = new ListFragment();
        Bundle bundle = new Bundle();
        if (id == R.id.experience_button) {
            bundle.putString("list", getString(R.string.experience));
            fragment.setArguments(bundle);
        } else if (id == R.id.personal_button) {
            PersonalDetailsFragment personalDetailsFragment = new PersonalDetailsFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, personalDetailsFragment)
                    .addToBackStack(PersonalDetailsFragment.class.getSimpleName())
                    .commit();
            return;
        } else if (id == R.id.education_button) {
            bundle.putString("list", getString(R.string.education));
            fragment.setArguments(bundle);
        } else if (id == R.id.skills_button) {
            bundle.putString("list", getString(R.string.skills));
            fragment.setArguments(bundle);
        } else {
            bundle.putString("list", getString(R.string.Internships));
            fragment.setArguments(bundle);
        }
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(ListFragment.class.getSimpleName())
                .commit();
    }
}
