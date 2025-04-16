package com.ftalaveram.habbbits.presentation.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

    private FragmentRegisterBinding binding;
    private RegisterViewModel registerViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerViewModel = new ViewModelProvider(requireActivity()).get(RegisterViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputsValidators()){
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