package com.ftalaveram.habbbits.presentation.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ftalaveram.habbbits.R;
import com.ftalaveram.habbbits.databinding.FragmentProfileBinding;
import com.ftalaveram.habbbits.presentation.activities.AuthActivity;
import com.ftalaveram.habbbits.presentation.activities.HomeActivity;
import com.ftalaveram.habbbits.presentation.activities.MainActivity;
import com.ftalaveram.habbbits.presentation.viewmodels.ProfileViewModel;
import com.ftalaveram.habbbits.repositories.models.ProfileResponse;

import java.util.Objects;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private ProfileViewModel profileViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
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

        binding.btnLogOut.setOnClickListener(v -> {
            new AlertDialog.Builder(requireContext())
                    .setTitle(getString(R.string.log_out))
                    .setMessage(getString(R.string.sure_log_out))
                    .setPositiveButton(getString(R.string.yes), (dialog, which) -> {
                        profileViewModel.logOut();

                        Intent intent = new Intent(requireActivity(), AuthActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        requireActivity().finish();
                    })
                    .setNegativeButton(getString(R.string.no), null)
                    .show();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        profileViewModel.rechargeProfileData();
    }
}