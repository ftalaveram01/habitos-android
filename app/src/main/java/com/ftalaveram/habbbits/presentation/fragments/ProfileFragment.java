package com.ftalaveram.habbbits.presentation.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ftalaveram.habbbits.R;
import com.ftalaveram.habbbits.databinding.FragmentProfileBinding;
import com.ftalaveram.habbbits.presentation.viewmodels.ProfileViewModel;
import com.ftalaveram.habbbits.repositories.models.ProfileResponse;

import java.util.Objects;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private ProfileViewModel profileViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        profileViewModel.profileLiveData.observe(getViewLifecycleOwner(), new Observer<ProfileResponse>() {
            @Override
            public void onChanged(ProfileResponse profileResponse) {
                binding.username.setText(profileResponse.getName());
                binding.email.setText(profileResponse.getEmail());
                binding.numberHabits.setText(String.valueOf(profileResponse.getHabitsNumber()));
            }
        });

        binding.btnUpdateProfile.setOnClickListener(v -> {
            Navigation.findNavController(ProfileFragment.this.requireView()).navigate(R.id.action_profileFragment_to_updateProfileFragment);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        profileViewModel.rechargeProfileData();
    }
}