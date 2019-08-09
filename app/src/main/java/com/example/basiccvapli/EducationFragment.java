package com.example.basiccvapli;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.basiccvapli.databinding.FragmentEducationBinding;
import com.example.basiccvapli.models.Education;
import com.example.basiccvapli.viewmodels.EducationViewModel;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Objects;

public class EducationFragment extends Fragment {

    private EducationViewModel viewModel;
    private String key;
    private Spinner perTypeSpinner;
    private Spinner educationSpinner;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentEducationBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_education, container, false);
        binding.setLifecycleOwner(this);
        viewModel = ViewModelProviders.of(this).get(EducationViewModel.class);
        viewModel.init();
        binding.setEducationviewmodel(viewModel);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel.update.setValue(getString(R.string.addEducation));

        perTypeSpinner = getView().findViewById(R.id.secondarySpinner);
        educationSpinner = getView().findViewById(R.id.degreeTypeSpinner);

        ArrayAdapter<CharSequence> perTypeSpinnerAdapter = ArrayAdapter.createFromResource(Objects.requireNonNull(getContext()), R.array.marksType, R.layout.support_simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> educationSpinnerAdapter = ArrayAdapter.createFromResource(Objects.requireNonNull(getContext()), R.array.educationType, R.layout.support_simple_spinner_dropdown_item);

        perTypeSpinner.setAdapter(perTypeSpinnerAdapter);
        educationSpinner.setAdapter(educationSpinnerAdapter);

        if (getArguments() != null) {
            Education education = getArguments().getParcelable(getString(R.string.education));
            if (education != null) {
                viewModel.instituteName.setValue(education.getInstituteName());
                viewModel.fieldOfStudy.setValue((education.getFieldOfStudy()));
                viewModel.from.setValue((education.getFromdate()));
                viewModel.to.setValue(education.getTodate());
                perTypeSpinner.setSelection(perTypeSpinnerAdapter.getPosition(education.getPertype()));
                educationSpinner.setSelection(educationSpinnerAdapter.getPosition(education.getDegreeType()));
                viewModel.marks.setValue(education.getPercentage());
                key = education.getEdid();
                viewModel.update.setValue(getString(R.string.updatedetails));
            }
        }
    }

    public class EducationHandler {

        public void onClickUpdateDetails() {
            String institute = viewModel.instituteName.getValue();
            String educationType = educationSpinner.getSelectedItem().toString();
            String perType = perTypeSpinner.getSelectedItem().toString();
            String percentage = viewModel.marks.getValue();
            String fieldOfStudy = viewModel.fieldOfStudy.getValue();
            String fromDate = viewModel.from.getValue();
            String toDate = viewModel.to.getValue();


            if (institute == null || institute.isEmpty() || educationType.isEmpty() || perType.isEmpty() || percentage == null || percentage.isEmpty() || fieldOfStudy == null || fieldOfStudy.isEmpty()
                    || fromDate == null || fromDate.isEmpty() || toDate == null || toDate.isEmpty()) {

                Toast.makeText(getContext(), "PLEASE FILL ALL CEREDENTIALS", Toast.LENGTH_SHORT).show();

            } else {
                Education education = new Education();
                education.setPertype(perType);
                education.setDegreeType(educationType);
                education.setFieldOfStudy(fieldOfStudy);
                education.setFromdate(fromDate);
                education.setInstituteName(institute);
                education.setTodate(toDate);
                education.setPercentage(percentage);
                if (key == null) {
                    viewModel.save(education);
                    Toast.makeText(getContext(), "EDUCATION ADDED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                } else {
                    viewModel.update(education, key);
                    Toast.makeText(getContext(), "EDUCATION UPDATED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                }
                CommonApplication.getFragmentManager().popBackStack();
            }
        }

        public void onClickDate(final View view) {
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
    }
}
