package com.example.basiccvapli;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import com.example.basiccvapli.databinding.FragmentInternshipsBinding;
import com.example.basiccvapli.models.Internship;
import com.example.basiccvapli.viewmodels.InternshipViewModel;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Objects;

public class InternshipsFragment extends Fragment {

    private InternshipViewModel viewModel;
    private FragmentManager fragmentManager;
    private String key;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentInternshipsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_internships, container, false);
        binding.setLifecycleOwner(this);
        viewModel = ViewModelProviders.of(this).get(InternshipViewModel.class);
        viewModel.init(getActivity());
        fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        binding.setInternshipviewmodel(viewModel);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        showDatePickerDialog();

        if (getArguments() != null) {
            Internship internship = getArguments().getParcelable(getString(R.string.Internships));
            if (internship != null) {
                viewModel.companyName.setValue(internship.getCompanyName());
                viewModel.industry.setValue(internship.getIndustry());
                viewModel.designation.setValue(internship.getDesignation());
                viewModel.location.setValue(internship.getLocation());
                viewModel.from.setValue(internship.getFromDate());
                viewModel.to.setValue(internship.getToDate());
                key = internship.getIid();
                ((Button) Objects.requireNonNull(getView()).findViewById(R.id.addInternshipButton)).setText(getString(R.string.updatedetails));
            }
        }

        Objects.requireNonNull(getView()).findViewById(R.id.addInternshipButton).setOnClickListener(new View.OnClickListener() {
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

                    Internship internship = new Internship();
                    internship.setCompanyName(companyName);
                    internship.setIndustry(industry);
                    internship.setDesignation(designation);
                    internship.setLocation(location);
                    internship.setFromDate(fromDate);
                    internship.setToDate(toDate);
                    if (key == null) {
                        viewModel.save(internship);
                        Toast.makeText(getContext(), "EXPERIENCE ADDED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                    } else {
                        viewModel.update(internship, key);
                        Toast.makeText(getContext(), "EXPERIENCE UPDATED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                    }
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
