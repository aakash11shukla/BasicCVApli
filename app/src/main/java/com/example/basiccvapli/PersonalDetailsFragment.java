package com.example.basiccvapli;


import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.basiccvapli.databinding.FragmentPersonalDetailsBinding;
import com.example.basiccvapli.models.PersonalDetails;
import com.example.basiccvapli.viewmodels.PersonalDetailsViewModel;
import com.google.firebase.database.DataSnapshot;

import java.io.IOException;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class PersonalDetailsFragment extends Fragment {

    private static final int GALLERY = 143;
    private static final int REQUEST_PERM_STORAGE = 165;
    private PersonalDetailsViewModel viewModel;
    private FragmentManager fragmentManager;
    private ImageView imageViewProfileImage;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(this).get(PersonalDetailsViewModel.class);
        viewModel.init(getActivity());
        FragmentPersonalDetailsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_personal_details, container, false);
        binding.setPersonaldetailsviewmodel(viewModel);
        binding.setLifecycleOwner(this);
        fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel.getDetails().observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    PersonalDetails personalDetails = dataSnapshot.child(getString(R.string.personalDetails)).getValue(PersonalDetails.class);
                    if (personalDetails != null) {
                        viewModel.filepath.setValue(personalDetails.getProfileImage());
                        viewModel.permanentAddress.setValue(personalDetails.getPermanentAddress());
                        viewModel.pinCode.setValue(personalDetails.getPincode());
                        viewModel.homeTown.setValue(personalDetails.getHometown());
                        viewModel.email.setValue(personalDetails.getEmail());
                        viewModel.aadharNo.setValue(personalDetails.getAadharNumber());
                        viewModel.name.setValue(personalDetails.getName());
                        viewModel.maritalStatus.setValue(personalDetails.getMaritialStatus());
                        viewModel.dob.setValue(personalDetails.getDateOfBirth());
                        viewModel.gender.setValue(personalDetails.getGender());
                    }
                }
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showDatePickerDialog();

        Button updatePersonalInfo = Objects.requireNonNull(getView()).findViewById(R.id.upadate_button);
        final Spinner genderDropdown = getView().findViewById(R.id.genderSpinnerDropdown);
        final Spinner maritialSpinnerDropdown = getView().findViewById(R.id.maritialSpinnerDropdown);

        ImageView editProfileImage = getView().findViewById(R.id.editprofile_image);
        imageViewProfileImage = getView().findViewById(R.id.imageprofile_image);

        editProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });

        updatePersonalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = viewModel.name.getValue();
                String aadhar = viewModel.aadharNo.getValue();
                String email = viewModel.email.getValue();
                String hometown = viewModel.homeTown.getValue();
                String pincode = viewModel.pinCode.getValue();
                String address = viewModel.permanentAddress.getValue();
                String maritialStatus = maritialSpinnerDropdown.getSelectedItem().toString();
                String gender = genderDropdown.getSelectedItem().toString();
                String dob = viewModel.dob.getValue();

                if (name == null || name.isEmpty() || aadhar == null || aadhar.isEmpty() || email == null || email.isEmpty() || hometown == null || hometown.isEmpty() || pincode.isEmpty() || address.isEmpty()
                        || maritialStatus.isEmpty() || gender.isEmpty() || dob == null || dob.isEmpty()) {

                    Toast.makeText(getContext(), "PLEASE FILL ALL THE CEREDENTIALS", Toast.LENGTH_SHORT).show();
                } else if (viewModel.filepath == null) {
                    Toast.makeText(getContext(), "PLEASE ADD A PROFILE IMAGE", Toast.LENGTH_SHORT).show();
                } else {
                    PersonalDetails personalDetails = new PersonalDetails();
                    personalDetails.setName(name);
                    personalDetails.setEmail(email);
                    personalDetails.setAadharNumber(aadhar);
                    personalDetails.setDateOfBirth(dob);
                    personalDetails.setGender(gender);
                    personalDetails.setHometown(hometown);
                    personalDetails.setMaritialStatus(maritialStatus);
                    personalDetails.setPermanentAddress(address);
                    personalDetails.setPincode(pincode);
                    personalDetails.setProfileImage(viewModel.filepath.getValue());
                    viewModel.save(personalDetails);
                    Toast.makeText(getContext(), "DETAILS UPDATED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                    fragmentManager.popBackStack();
                }
            }
        });

        viewModel.filepath.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(!s.isEmpty()) {
                    setProfileImage();
                    Toast.makeText(getContext(), "IMAGE SET", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setProfileImage() {
        if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(getContext()), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERM_STORAGE);
        } else {
            setBitmap();
        }
    }

    private void setBitmap() {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(Objects.requireNonNull(getActivity()).getContentResolver(), Uri.parse(viewModel.filepath.getValue()));
            if (bitmap != null) {
                imageViewProfileImage.setImageBitmap(bitmap);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showDatePickerDialog() {
        final TextView dobDatePickerView = Objects.requireNonNull(getView()).findViewById(R.id.dob);

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        viewModel.dob.setValue(dayOfMonth + " " + new DateFormatSymbols().getShortMonths()[month] + " " + year);
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

        dobDatePickerView.setOnClickListener(clickListener);
    }

    private void chooseImage() {
        Intent i = new Intent(
                Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, GALLERY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY) {
            if (resultCode == RESULT_OK && data != null && data.getData() != null) {
                Uri filepath = data.getData();
                viewModel.filepath.setValue(filepath.toString());
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERM_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setBitmap();
            }
        }
    }
}
