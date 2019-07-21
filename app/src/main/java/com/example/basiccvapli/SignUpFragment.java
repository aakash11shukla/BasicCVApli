package com.example.basiccvapli;


import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.basiccvapli.databinding.FragmentSignupBinding;
import com.example.basiccvapli.viewmodels.SignUpViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class SignUpFragment extends Fragment {

    private TextInputLayout phone;
    private TextInputLayout otp;
    private Button submit;
    private Button code;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;

    private FirebaseAuth mAuth;

    private FragmentSignupBinding binding;
    private SignUpViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_signup, container, false);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        mAuth.useAppLanguage();

        viewModel = ViewModelProviders.of(this).get(SignUpViewModel.class);
        binding.setSignupviewmodel(viewModel);

        phone = getView().findViewById(R.id.phone_number);
        otp = getView().findViewById(R.id.otp);
        submit = getView().findViewById(R.id.submit);
        code = getView().findViewById(R.id.code);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = viewModel.phone_no.getValue();
                if (otp.getVisibility() == View.GONE) {
                    if (number == null || number.isEmpty()) {
                        phone.getEditText().setError("Cannot be empty");
                    } else {
                        if (!Patterns.PHONE.matcher(number).matches()) {
                            phone.getEditText().setError("Invalid format");
                        }
                        viewModel.phone_no.setValue(null);
                        verifyPhoneNumber(number, mResendToken);
                    }
                } else {
                    Toast.makeText(getContext(), "SMS sent to phone number.", Toast.LENGTH_SHORT).show();
                    verifyPhoneNumber(number, mResendToken);
                }
            }
        });

        code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vcode = viewModel.code.getValue();
                if (vcode == null || vcode.isEmpty()) {
                    return;
                }
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, vcode);
                signInWithPhoneAuthCredential(credential);
            }
        });

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                Toast.makeText(getContext(), "Code automatically verified", Toast.LENGTH_SHORT).show();
                viewModel.code.setValue(credential.getSmsCode());
                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                if (e instanceof FirebaseTooManyRequestsException) {
                    getActivity().finish();
                }
                phone.setError("Verification Failed");
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                mVerificationId = verificationId;
                mResendToken = token;
                phone.setVisibility(View.GONE);
                otp.setVisibility(View.VISIBLE);
                submit.setText("RESEND");
                code.setVisibility(View.VISIBLE);
            }
        };
    }

    private void verifyPhoneNumber(String number, PhoneAuthProvider.ForceResendingToken token) {
        if (token == null) {
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    number,
                    60,
                    TimeUnit.SECONDS,
                    getActivity(),
                    mCallbacks);
        } else {
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    number,
                    60,
                    TimeUnit.SECONDS,
                    getActivity(),
                    mCallbacks,
                    token);
        }
    }

    private void signInWithPhoneAuthCredential(final PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = task.getResult().getUser();
                            if (user.getMetadata().getCreationTimestamp() != user.getMetadata().getLastSignInTimestamp()) {
                                Toast.makeText(getContext(), "User already exist with this number.", Toast.LENGTH_LONG).show();
                                return;
                            }
                            user.updatePhoneNumber(credential);
                            PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putBoolean(getString(R.string.detailsCompletedKey), false).apply();
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_container, new DetailsFragment())
                                    .commit();
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(getContext(), "Invalid verification code.", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }
}
