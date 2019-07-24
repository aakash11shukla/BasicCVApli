package com.example.basiccvapli;


import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.basiccvapli.databinding.FragmentDetailsBinding;
import com.example.basiccvapli.firebaseUtils.FirebaseUtil;
import com.example.basiccvapli.viewmodels.DetailsViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class DetailsFragment extends Fragment {

    private EditText email;

    private FragmentDetailsBinding binding;
    private DetailsViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        email = getView().findViewById(R.id.email);
        Button submit = getView().findViewById(R.id.details);

        viewModel = ViewModelProviders.of(this).get(DetailsViewModel.class);
        binding.setDetailsviewmodel(viewModel);
        viewModel.init(getActivity());

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = viewModel.name.getValue();
                String useremail = viewModel.email.getValue();
                String userpasswd = viewModel.password.getValue();
                String userloc = viewModel.location.getValue();
                if (username == null || username.isEmpty() || useremail == null || useremail.isEmpty() || userpasswd == null || userpasswd.isEmpty() || userloc == null || userloc.isEmpty()) {
                    Toast.makeText(getContext(), "Fields cannot be empty.", Toast.LENGTH_SHORT).show();
                } else {
                    viewModel.name.setValue(null);
                    viewModel.email.setValue(null);
                    viewModel.password.setValue(null);
                    viewModel.location.setValue(null);
                    validate(username, useremail, userpasswd, userloc);
                }
            }
        });
    }

    private void validate(final String username, final String useremail, final String userpasswd, final String userloc) {
        if (!Patterns.EMAIL_ADDRESS.matcher(useremail).matches()) {
            email.setError("Invalid email id");
            return;
        }

        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        final FirebaseUser user = auth.getCurrentUser();
        AuthCredential credential = EmailAuthProvider.getCredential(useremail, userpasswd);
        if (user != null) {
            user.linkWithCredential(credential)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUtil.databaseReference = db.collection("candidates").document(useremail);
                                Map<String, String> map = new HashMap<>();
                                map.put("name", username);
                                map.put("password", userpasswd);
                                map.put("timestamp", new SimpleDateFormat("MMM d, yyyy 'at' H:mm:ss a z", Locale.ENGLISH).format(new Date()));
                                Map<String, String> hashmap = new HashMap<>();
                                hashmap.put("name", username);
                                hashmap.put("email", useremail);
                                hashmap.put("ph_no", Objects.requireNonNull(user.getPhoneNumber()));
                                hashmap.put("address", userloc);
                                viewModel.saveUserDetails(useremail, map);
                                viewModel.saveDetails(hashmap, getActivity(), userpasswd);
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
