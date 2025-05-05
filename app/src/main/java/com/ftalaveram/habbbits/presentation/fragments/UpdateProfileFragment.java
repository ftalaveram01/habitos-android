package com.ftalaveram.habbbits.presentation.fragments;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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
                        mostrarDialogCompleto(getString(R.string.profileUpdated), view);
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

    private void mostrarDialogCompleto(String mensaje, View view) {
        Context context = view.getContext();

        Dialog dialog = new Dialog(context, R.style.DialogNoTitle);
        dialog.setContentView(R.layout.dialog_habito_completado);

        TextView tvTitulo = dialog.findViewById(R.id.tvTitulo);
        ImageView ivIcono = dialog.findViewById(R.id.ivIcono);
        Button btnAceptar = dialog.findViewById(R.id.btnAceptar);

        tvTitulo.setText(mensaje);

        Animation anim = AnimationUtils.loadAnimation(context, R.anim.bounce);
        ivIcono.startAnimation(anim);

        btnAceptar.setOnClickListener(v -> dialog.dismiss());

        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        dialog.show();
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