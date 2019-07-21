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

import com.example.basiccvapli.databinding.FragmentLoginBinding;
import com.example.basiccvapli.firebaseUtils.FirebaseUtil;
import com.example.basiccvapli.viewmodels.LoginViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginFragment extends Fragment {

    private EditText email;
    private EditText password;
    private Button submit;

    private FirebaseAuth mAuth;

    private FragmentLoginBinding binding;
    private LoginViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        binding.setLoginviewmodel(viewModel);

        email = getView().findViewById(R.id.email);
        password = getView().findViewById(R.id.password);
        submit = getView().findViewById(R.id.submit);

        mAuth = FirebaseAuth.getInstance();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = viewModel.email.getValue();
                String passwd = viewModel.passwd.getValue();

                if (userEmail == null || userEmail.isEmpty() || passwd == null || passwd.isEmpty()) {
                    Toast.makeText(getContext(), "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
                    email.setError("Invalid format");
                    return;
                }
                viewModel.email.setValue(null);
                viewModel.passwd.setValue(null);
                loginUser(userEmail, passwd);

            }
        });
    }

    private void loginUser(final String userEmail, final String passwd) {

        final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("candidates").document(userEmail).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot snapshot = task.getResult();
                            if (snapshot != null) {
                                if (snapshot.exists()) {
                                    firebaseFirestore.collection("users").document(userEmail).get()
                                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        DocumentSnapshot documentSnapshot = task.getResult();
                                                        if (passwd.equals(documentSnapshot.get("password"))) {
                                                            FirebaseUtil.databaseReference = firebaseFirestore.collection("candidates").document(userEmail);
                                                            getActivity().getSupportFragmentManager().beginTransaction()
                                                                    .replace(R.id.fragment_container, new BasiccvFragment())
                                                                    .commit();
                                                        } else {
                                                            Toast.makeText(getContext(), "Password don't match", Toast.LENGTH_LONG).show();
                                                        }
                                                    }
                                                }
                                            });
                                } else {
                                    Toast.makeText(getContext(), "Email doesn't exists", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    }
                });
    }
}
