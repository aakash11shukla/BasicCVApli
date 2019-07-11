package com.example.basiccvapli;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.basiccvapli.adapters.ExperienceAdapter;
import com.example.basiccvapli.models.Experience;
import com.example.basiccvapli.viewmodels.ExperienceViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.GenericTypeIndicator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class ExperienceListFragment extends Fragment {

    private FragmentManager fragmentManager;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_experience_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ExperienceViewModel viewModel = ViewModelProviders.of(this).get(ExperienceViewModel.class);
        viewModel.init(getContext());

        Objects.requireNonNull(getView()).findViewById(R.id.add_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, new ExperienceFragment())
                        .addToBackStack(ExperienceFragment.class.getSimpleName())
                        .commit();
            }
        });

        RecyclerView recyclerView = Objects.requireNonNull(getView()).findViewById(R.id.experiences_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        final ExperienceAdapter adapter = new ExperienceAdapter();
        recyclerView.setAdapter(adapter);

        viewModel.getDetails().observe(getViewLifecycleOwner(), new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                ArrayList<Experience> experiences = new ArrayList<>();
                for(DataSnapshot ds: dataSnapshot.child("Experience").getChildren()){
                    experiences.add(ds.getValue(Experience.class));
                }
                adapter.submitList(experiences);
            }
        });
    }
}
