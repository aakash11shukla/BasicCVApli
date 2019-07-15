package com.example.basiccvapli;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.basiccvapli.adapters.EducationAdapter;
import com.example.basiccvapli.adapters.ExperienceAdapter;
import com.example.basiccvapli.adapters.InternshipAdapter;
import com.example.basiccvapli.adapters.SkillAdapter;
import com.example.basiccvapli.models.Education;
import com.example.basiccvapli.models.Experience;
import com.example.basiccvapli.models.Internship;
import com.example.basiccvapli.models.Skill;
import com.example.basiccvapli.viewmodels.EducationViewModel;
import com.example.basiccvapli.viewmodels.ExperienceViewModel;
import com.example.basiccvapli.viewmodels.SkillViewModel;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.Objects;


public class ListFragment extends Fragment {

    private FragmentManager fragmentManager;
    private Fragment fragment;
    private String tag;
    private ListAdapter adapter;

    private String type;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LiveData<DataSnapshot> liveData = null;

        if (getArguments() != null) {
            type = getArguments().getString("list");
            if (type != null) {
                ViewModel viewModel;
                if (type.equals(getString(R.string.experience))) {
                    viewModel = ViewModelProviders.of(this).get(ExperienceViewModel.class);
                    ((ExperienceViewModel) viewModel).init(getContext());
                    liveData = ((ExperienceViewModel) viewModel).getDetails();
                    fragment = new ExperienceFragment();
                    tag = ExperienceFragment.class.getSimpleName();
                    adapter = new ExperienceAdapter(null);
                } else if (type.equals(getString(R.string.education))) {
                    viewModel = ViewModelProviders.of(this).get(EducationViewModel.class);
                    ((EducationViewModel) viewModel).init(getContext());
                    liveData = ((EducationViewModel) viewModel).getDetails();
                    fragment = new EducationFragment();
                    tag = EducationFragment.class.getSimpleName();
                } else if (type.equals(getString(R.string.skills))) {
                    viewModel = ViewModelProviders.of(this).get(SkillViewModel.class);
                    ((SkillViewModel) viewModel).init(getContext());
                    liveData = ((SkillViewModel) viewModel).getDetails();
                    fragment = new SkillsFragment();
                    tag = SkillsFragment.class.getSimpleName();
                    adapter = new SkillAdapter(null);
                } else if (type.equals(getString(R.string.Internships))) {
                    fragment = new InternshipsFragment();
                    tag = InternshipsFragment.class.getSimpleName();
                }
            }
        }


        Objects.requireNonNull(getView()).findViewById(R.id.add_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack(tag)
                        .commit();
            }
        });

        final RecyclerView recyclerView = Objects.requireNonNull(getView()).findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        if (liveData != null) {
            liveData.observe(getViewLifecycleOwner(), new Observer<DataSnapshot>() {
                @Override
                public void onChanged(DataSnapshot dataSnapshot) {
                    ArrayList<Experience> experiences = new ArrayList<>();
                    ArrayList<Skill> skills = new ArrayList<>();
                    ArrayList<Education> educations = new ArrayList<>();
                    ArrayList<Internship> internships = new ArrayList<>();
                    if (dataSnapshot != null && dataSnapshot.child(type).hasChildren()) {
                        for (DataSnapshot ds : dataSnapshot.child(type).getChildren()) {
                            if (type.equals(getString(R.string.experience))) {
                                setExperienceAdapter(ds, experiences);
                            } else if (type.equals(getString(R.string.skills))) {
                                setSkillAdapter(ds, skills);
                            } else if (type.equals(getString(R.string.Internships))) {
                                setInternshipAdapter(ds, internships);
                            } else if (type.equals(getString(R.string.education))) {
                                setEducationAdapter(ds, educations);
                            }
                        }
                        recyclerView.setAdapter(adapter);
                        if (type.equals(getString(R.string.experience))) {
                            ((ExperienceAdapter) adapter).submitList(experiences);
                        } else if (type.equals(getString(R.string.skills))) {
                            ((SkillAdapter) adapter).submitList(skills);
                        } else if (type.equals(getString(R.string.Internships))) {
                            ((InternshipAdapter) adapter).submitList(internships);
                        } else if (type.equals(getString(R.string.education))) {
                            ((EducationAdapter) adapter).submitList(educations);
                        }
                    }
                }
            });
        }
    }

    private void setSkillAdapter(DataSnapshot ds, ArrayList<Skill> skills) {
        final Skill skill = ds.getValue(Skill.class);
        skill.setSid(ds.getKey());
        skills.add(skill);
        adapter = new SkillAdapter(new SkillAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(getString(R.string.skills), skill);
                fragment.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack(getString(R.string.experience))
                        .commit();
            }
        });
    }

    public void setExperienceAdapter(DataSnapshot ds, ArrayList<Experience> experiences) {
        final Experience experience = ds.getValue(Experience.class);
        experience.setEid(ds.getKey());
        experiences.add(experience);
        adapter = new ExperienceAdapter(new ExperienceAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(getString(R.string.experience), experience);
                fragment.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack(tag)
                        .commit();
            }
        });
    }

    public void setInternshipAdapter(DataSnapshot ds, ArrayList<Internship> internships) {
        final Internship internship = ds.getValue(Internship.class);
        internship.setIid(ds.getKey());
        internships.add(internship);
        adapter = new ExperienceAdapter(new ExperienceAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(getString(R.string.experience), internship);
                fragment.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack(tag)
                        .commit();
            }
        });
    }

    public void setEducationAdapter(DataSnapshot ds, ArrayList<Education> educations){
        final Education education = ds.getValue(Education.class);
        education.setEdid(ds.getKey());
        educations.add(education);
        adapter = new EducationAdapter(new EducationAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(getString(R.string.education), education);
                fragment.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack(tag)
                        .commit();
            }
        });
    }
}
