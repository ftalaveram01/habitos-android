package com.ftalaveram.habbbits.presentation.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ftalaveram.habbbits.R;
import com.ftalaveram.habbbits.databinding.FragmentLoginBinding;
import com.ftalaveram.habbbits.databinding.FragmentRegisterBinding;
import com.ftalaveram.habbbits.presentation.viewmodels.RegisterViewModel;
import com.ftalaveram.habbbits.repositories.models.RegisterResponse;

public class RegisterFragment extends Fragment {

    FragmentRegisterBinding binding;

    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        RegisterViewModel registerViewModel = new ViewModelProvider(requireActivity()).get(RegisterViewModel.class);

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputsValidators()){
                    Log.d("DEBUG", "Ha pasado las validacines y va a ir al viewmodel a register");
                    registerViewModel.register(binding.nameInput.getText().toString(), binding.emailInput.getText().toString(), binding.passwordInput.getText().toString());
                }
            }
        });

        registerViewModel.registerData.observe(getViewLifecycleOwner(), new Observer<RegisterResponse>() {
            @Override
            public void onChanged(RegisterResponse registerResponse) {
                if (registerResponse.isSuccess()){
                    Navigation.findNavController(RegisterFragment.this.getView()).navigate(R.id.action_registerFragment_to_loginFragment);
                }
            }
        });


        return binding.getRoot();
    }

    private boolean inputsValidators() {
        if (binding.emailInput.getText().length() == 0 || binding.nameInput.getText().length() == 0 || binding.passwordInput.getText().length() == 0 || binding.confirmPasswordInput.getText().length() == 0){
            Log.d("ERROR DE VALIDACION", "ALGUN CAMPO VACIO");
            return false;
        }
        if (!(binding.passwordInput.getText().toString().equals(binding.confirmPasswordInput.getText().toString()))){
            Log.d("ERROR DE VALIDACION", "LAS CONTRASEÑAS NO COINCIDEN");
            return false;
        }
        return true;
    }
}