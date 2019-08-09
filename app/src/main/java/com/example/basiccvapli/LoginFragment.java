package com.example.basiccvapli;


import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import com.example.basiccvapli.databinding.FragmentLoginBinding;
import com.example.basiccvapli.viewmodels.LoginViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class LoginFragment extends Fragment {

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
    }

    public class LoginHandler {

        public void onClickSubmit() {
            String userEmail = viewModel.email.getValue();
            String passwd = viewModel.passwd.getValue();

            if (userEmail == null || userEmail.isEmpty() || passwd == null || passwd.isEmpty()) {
                Toast.makeText(getContext(), "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
                Toast.makeText(getContext(), "Invalid Format!!", Toast.LENGTH_SHORT).show();
                return;
            }
            viewModel.email.setValue(null);
            viewModel.passwd.setValue(null);
            loginUser(userEmail, passwd);
        }

    }

    private void loginUser(final String userEmail, final String passwd) {
        CommonApplication.getFirebaseAuth().signInWithEmailAndPassword(userEmail, passwd)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            CommonApplication.getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                            Toast.makeText(getContext(), "Welcome", Toast.LENGTH_SHORT).show();
                            CommonApplication.getFragmentManager().beginTransaction()
                                    .add(R.id.fragment_container, new BasiccvFragment())
                                    .commit();
                        }
                    }
                });
    }
}
