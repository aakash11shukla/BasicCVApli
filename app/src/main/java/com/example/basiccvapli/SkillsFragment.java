package com.example.basiccvapli;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import com.example.basiccvapli.databinding.FragmentSkillsBinding;
import com.example.basiccvapli.models.Skill;
import com.example.basiccvapli.viewmodels.SkillViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class SkillsFragment extends Fragment {

    private SkillViewModel viewModel;
    private FragmentManager fragmentManager;
    private String key;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentSkillsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_skills, container, false);
        binding.setLifecycleOwner(this);
        fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        viewModel = ViewModelProviders.of(this).get(SkillViewModel.class);
        viewModel.init();
        binding.setSkillviewmodel(viewModel);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final TextInputEditText skillEdittext = Objects.requireNonNull(getView()).findViewById(R.id.skills_edittext);

        final Spinner skilllevelSpinner = getView().findViewById(R.id.skillLevel_spinner);
        Button addSkillButton = getView().findViewById(R.id.addSkillButton);

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(Objects.requireNonNull(getContext()), R.array.skillsList, android.R.layout.simple_spinner_item);
        skilllevelSpinner.setAdapter(spinnerAdapter);
        if (getArguments() != null) {
            Skill skill = getArguments().getParcelable(getString(R.string.skills));
            if (skill != null) {
                viewModel.skillName.setValue(skill.getSkillName());
                skilllevelSpinner.setSelection(spinnerAdapter.getPosition(skill.getSkillLevel()));
                key = skill.getSid();
                addSkillButton.setText(getString(R.string.updatedetails));
            }
        }

        addSkillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String skillName = Objects.requireNonNull(Objects.requireNonNull(skillEdittext).getText()).toString();
                String skillLevel = skilllevelSpinner.getSelectedItem().toString();

                if (skillName.isEmpty() || skillLevel.equalsIgnoreCase("PROFICIENCY LEVEL")) {
                    Toast.makeText(getContext(), "PLEASE FILL IN ALL CEREDENTIALS", Toast.LENGTH_SHORT).show();
                } else {
                    Skill skill = new Skill();
                    skill.setSkillName(skillName);
                    skill.setSkillLevel(skillLevel);
                    if (key == null) {
                        viewModel.save(skill);
                        Toast.makeText(getContext(), "EXPERIENCE ADDED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                    } else {
                        viewModel.update(skill, key);
                        Toast.makeText(getContext(), "EXPERIENCE ADDED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                    }
                    fragmentManager.popBackStack();
                }
            }
        });
    }
}
