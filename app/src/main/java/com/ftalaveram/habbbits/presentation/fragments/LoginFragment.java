package com.ftalaveram.habbbits.presentation.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ftalaveram.habbbits.R;
import com.ftalaveram.habbbits.databinding.FragmentLoginBinding;
import com.ftalaveram.habbbits.presentation.activities.HomeActivity;
import com.ftalaveram.habbbits.presentation.viewmodels.LoginViewModel;
import com.ftalaveram.habbbits.repositories.models.LoginData;

import java.util.Objects;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private LoginViewModel loginViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginViewModel.login(Objects.requireNonNull(binding.usernameInput.getText()).toString(), Objects.requireNonNull(binding.passwordInput.getText()).toString());
            }
        });

        loginViewModel.loginData.observe(getViewLifecycleOwner(), new Observer<LoginData>() {
            @Override
            public void onChanged(LoginData loginData) {
                if (loginData.isSuccess()){
                    startActivity(new Intent(requireActivity(), HomeActivity.class));
                    requireActivity().finish();
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}