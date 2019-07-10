package com.example.basiccvapli;


import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.basiccvapli.databinding.FragmentExperienceBinding;
import com.example.basiccvapli.models.Experience;
import com.example.basiccvapli.viewmodels.ExperienceViewModel;
import com.google.firebase.database.DataSnapshot;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Objects;

public class ExperienceFragment extends Fragment {

    private ExperienceViewModel viewModel;
    private FragmentExperienceBinding binding;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(ExperienceViewModel.class);
        viewModel.init(getContext());

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

                String companyName = viewModel.companyName.getValue();
                String designation = viewModel.designation.getValue();
                String industry = viewModel.industry.getValue();
                String location = viewModel.location.getValue();
                String fromDate = viewModel.from.getValue();
                String toDate = viewModel.to.getValue();

                if (companyName == null || companyName.isEmpty() || designation == null || designation.isEmpty() || industry == null || industry.isEmpty() || location == null || location.isEmpty() || fromDate == null || fromDate.equals(getString(R.string.click_to_set_the_date)) || toDate == null || toDate.equals(getString(R.string.click_to_set_the_date))) {
                    Toast.makeText(getContext(), "PLEASE FILL IN ALL CEREDENTIALS", Toast.LENGTH_SHORT).show();
                } else {

                    viewModel.companyName.setValue("");
                    viewModel.industry.setValue("");
                    viewModel.designation.setValue("");
                    viewModel.location.setValue("");
                    viewModel.from.setValue("");
                    viewModel.to.setValue("");

                    Experience experience = new Experience();
                    experience.setCompanyName(companyName);
                    experience.setIndustry(industry);
                    experience.setDesignation(designation);
                    experience.setLocation(location);
                    experience.setFromDate(fromDate);
                    experience.setToDate(toDate);
                    viewModel.save(experience);
                    Toast.makeText(getContext(), "EXPERIENCE ADDED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
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
