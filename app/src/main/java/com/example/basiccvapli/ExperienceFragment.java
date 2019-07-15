package com.example.basiccvapli;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import com.example.basiccvapli.databinding.FragmentExperienceBinding;
import com.example.basiccvapli.viewmodels.ExperienceViewModel;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Objects;

public class ExperienceFragment extends Fragment {

    private ExperienceViewModel viewModel;
    private FragmentExperienceBinding binding;
    private FragmentManager fragmentManager;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(ExperienceViewModel.class);
        viewModel.init(getContext());
        fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        binding.setExperienceviewmodel(viewModel);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_experience, container, false);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        showDatePickerDialog();

        Objects.requireNonNull(getView()).findViewById(R.id.addExperienceButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewModel.anyBlank()) {
                    Toast.makeText(getContext(), "PLEASE FILL IN ALL CEREDENTIALS", Toast.LENGTH_SHORT).show();
                } else {
                    viewModel.save();
                    Toast.makeText(getContext(), "EXPERIENCE ADDED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                    fragmentManager.popBackStack();
                }
            }
        });

    }

    private void showDatePickerDialog() {
        final TextView fromDatePickerView = Objects.requireNonNull(getView()).findViewById(R.id.from);
        final TextView toDatePickerView = Objects.requireNonNull(getView()).findViewById(R.id.to);

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        if (view.getId() == R.id.from) {
                            viewModel.from.setValue(dayOfMonth + " " + new DateFormatSymbols().getShortMonths()[month] + " " + year);
                        } else {
                            viewModel.to.setValue(dayOfMonth + " " + new DateFormatSymbols().getShortMonths()[month] + " " + year);
                        }
                    }
                };

                Calendar now = Calendar.getInstance();
                int year = now.get(java.util.Calendar.YEAR);
                int month = now.get(java.util.Calendar.MONTH);
                int day = now.get(java.util.Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(Objects.requireNonNull(getContext()), onDateSetListener, year, month, day);
                datePickerDialog.setTitle("Please select date.");
                datePickerDialog.show();
            }
        };

        fromDatePickerView.setOnClickListener(clickListener);
        toDatePickerView.setOnClickListener(clickListener);
    }

}
