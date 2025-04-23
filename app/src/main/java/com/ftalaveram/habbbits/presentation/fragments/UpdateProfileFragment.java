package com.ftalaveram.habbbits.presentation.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ftalaveram.habbbits.R;
import com.ftalaveram.habbbits.databinding.FragmentProfileBinding;
import com.ftalaveram.habbbits.databinding.FragmentUpdateProfileBinding;
import com.ftalaveram.habbbits.presentation.viewmodels.ProfileViewModel;
import com.ftalaveram.habbbits.repositories.models.ProfileResponse;
import com.ftalaveram.habbbits.repositories.models.UpdateResponse;

import java.util.Objects;

public class UpdateProfileFragment extends Fragment {

    FragmentUpdateProfileBinding binding;
    private ProfileViewModel profileViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUpdateProfileBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupPasswordModal();

        profileViewModel.updateLiveData.setValue(null);

        ProfileResponse profileResponse = profileViewModel.profileLiveData.getValue();

        if (profileResponse != null && !profileResponse.getName().isEmpty())
            binding.username.setText(profileResponse.getName());
        else
            binding.username.setText(getString(R.string.empty));

        if (profileResponse != null && !profileResponse.getEmail().isEmpty())
            binding.email.setText(profileResponse.getEmail());
        else
            binding.email.setText(getString(R.string.empty));

        binding.btnUpdateProfile.setOnClickListener(v -> {
            if (validName() && validEmail())
                profileViewModel.updateProfile(Objects.requireNonNull(binding.email.getText()).toString(), Objects.requireNonNull(binding.username.getText()).toString());
        });

        profileViewModel.updateLiveData.observe(getViewLifecycleOwner(), new Observer<UpdateResponse>() {
            @Override
            public void onChanged(UpdateResponse updateResponse) {
                if (updateResponse != null){
                    if (updateResponse.isSuccess()){
                        Toast.makeText(requireContext(), "User data updated", Toast.LENGTH_LONG).show();
                        Navigation.findNavController(view).navigateUp();
                    }else{
                        Toast.makeText(requireContext(), "UPDATE FAILED", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });



    }

    private void setupPasswordModal(){
        binding.btnUpdatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModalChangePasswordFragment modal = new ModalChangePasswordFragment();
                modal.show(getChildFragmentManager(), "ModalChangePassword");
            }
        });
    }

    private boolean validEmail(){
        if (!Objects.equals(Objects.requireNonNull(binding.email.getText()).toString(), "")){
            return true;
        }
        binding.email.setError(getString(R.string.required));
        return false;
    }

    private boolean validName(){
        if (!Objects.equals(Objects.requireNonNull(binding.username.getText()).toString(), "")){
            return true;
        }
        binding.username.setError(getString(R.string.required));
        return false;
    }
}