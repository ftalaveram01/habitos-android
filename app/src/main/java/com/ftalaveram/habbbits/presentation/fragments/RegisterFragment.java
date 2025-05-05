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
import android.widget.Toast;

import com.ftalaveram.habbbits.R;
import com.ftalaveram.habbbits.databinding.FragmentRegisterBinding;
import com.ftalaveram.habbbits.presentation.viewmodels.RegisterViewModel;
import com.ftalaveram.habbbits.repositories.models.RegisterResponse;

import java.util.Objects;

public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;
    private RegisterViewModel registerViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerViewModel = new ViewModelProvider(requireActivity()).get(RegisterViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
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
                if (validName() && validEmail() && validPassword() && validConfirmPassword() && validPasswordMatch()){
                    if (inputsValidators()){
                        registerViewModel.register(binding.nameInput.getText().toString(), binding.emailInput.getText().toString(), binding.passwordInput.getText().toString());
                    }
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
            Toast.makeText(getContext(), getString(R.string.update_not_valid), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!(binding.passwordInput.getText().toString().equals(binding.confirmPasswordInput.getText().toString()))){
            Log.d("ERROR DE VALIDACION", "LAS CONTRASEÑAS NO COINCIDEN");
            return false;
        }
        return true;
    }

    private boolean validName(){
        if (Objects.equals(Objects.requireNonNull(binding.nameInput.getText()).toString(), "")){
            binding.nameInput.setError(getString(R.string.required));
            return false;
        }
        return true;
    }

    private boolean validEmail(){
        if (Objects.equals(Objects.requireNonNull(binding.emailInput.getText()).toString(), "")){
            binding.emailInput.setError(getString(R.string.required));
            return false;
        }
        return true;
    }

    private boolean validPassword(){
        if (!Objects.equals(Objects.requireNonNull(binding.passwordInput.getText()).toString(), "")){
            return true;
        }
        binding.passwordInput.setError(getString(R.string.required));
        return false;
    }

    private boolean validConfirmPassword(){
        if (!Objects.equals(Objects.requireNonNull(binding.confirmPasswordInput.getText()).toString(), "")){
            return true;
        }
        binding.confirmPasswordInput.setError(getString(R.string.required));
        return false;
    }

    private boolean validPasswordMatch(){
        if (Objects.equals(Objects.requireNonNull(binding.passwordInput.getText()).toString(), Objects.requireNonNull(binding.confirmPasswordInput.getText()).toString())){
            return true;
        }
        binding.passwordInput.setError(getString(R.string.must_be_same));
        binding.confirmPasswordInput.setError(getString(R.string.must_be_same));
        return false;
    }
}