package com.ftalaveram.habbbits.presentation.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ftalaveram.habbbits.R;
import com.ftalaveram.habbbits.databinding.FragmentProfileBinding;
import com.ftalaveram.habbbits.presentation.activities.AuthActivity;
import com.ftalaveram.habbbits.presentation.viewmodels.ProfileViewModel;
import com.ftalaveram.habbbits.repositories.models.ProfileResponse;

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

        binding.btnLogOut.setOnClickListener(this::mostrarDialogLogout);
    }

    private void mostrarDialogLogout(View view) {
        Context context = view.getContext();

        Dialog dialog = new Dialog(context, R.style.DialogNoTitle);
        dialog.setContentView(R.layout.dialog_logout);

        TextView tvTitulo = dialog.findViewById(R.id.tvTitulo);
        TextView tvMensaje = dialog.findViewById(R.id.tvMensaje);
        ImageView ivIcono = dialog.findViewById(R.id.ivIcono);
        Button btnConfirmar = dialog.findViewById(R.id.btnConfirmar);
        Button btnCancelar = dialog.findViewById(R.id.btnCancelar);

        tvTitulo.setText(R.string.log_out);
        tvMensaje.setText(R.string.sure_log_out);
        ivIcono.setImageResource(R.drawable.salir);
        btnConfirmar.setText(R.string.yes);
        btnCancelar.setText(R.string.no);

        Animation anim = AnimationUtils.loadAnimation(context, R.anim.bounce);
        ivIcono.startAnimation(anim);

        btnConfirmar.setOnClickListener(v -> {
            profileViewModel.logOut();
            Intent intent = new Intent(context, AuthActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            requireActivity().finish();
            dialog.dismiss();
        });

        btnCancelar.setOnClickListener(v -> dialog.dismiss());

        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        dialog.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        profileViewModel.rechargeProfileData();
    }
}