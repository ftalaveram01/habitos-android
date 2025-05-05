package com.ftalaveram.habbbits.presentation.fragments;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ftalaveram.habbbits.R;
import com.ftalaveram.habbbits.databinding.ModalPasswordBinding;
import com.ftalaveram.habbbits.presentation.viewmodels.ProfileViewModel;
import com.ftalaveram.habbbits.repositories.models.UpdateResponse;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

public class ModalChangePasswordFragment extends DialogFragment {

    private ProfileViewModel profileViewModel;
    private EditText passwordEditText;
    private EditText newPasswordEditText;
    private EditText confirmPasswordEditText;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.modal_password, container, true);

        passwordEditText = view.findViewById(R.id.password);
        newPasswordEditText = view.findViewById(R.id.new_password);
        confirmPasswordEditText = view.findViewById(R.id.confirm_password);

        view.findViewById(R.id.btn_close_modal).setOnClickListener(v -> dismiss());

        view.findViewById(R.id.btn_confirm_update).setOnClickListener(v -> {
            if (validPassword() && validNewPassword() && validConfirmPassword() && validPasswordMatch()){
                Snackbar.make(requireView(), getString(R.string.tryingToChangePassword), 600).show();
                profileViewModel.updatePassword(newPasswordEditText.getText().toString(), passwordEditText.getText().toString());

                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    profileViewModel.updatePasswordLiveData.observe(getViewLifecycleOwner(), updateResponse -> {
                        if (updateResponse != null) {
                            if (updateResponse.isSuccess()) {
                                mostrarDialogCompleto(getString(R.string.passwordUpdated), v);
                                dismiss();
                            } else {
                                passwordEditText.setText(getString(R.string.empty));
                                newPasswordEditText.setText(getString(R.string.empty));
                                confirmPasswordEditText.setText(getString(R.string.empty));
                                Toast.makeText(requireContext(), getString(R.string.failChangingPassword), Toast.LENGTH_SHORT).show();
                            }
                            profileViewModel.updatePasswordLiveData.removeObservers(getViewLifecycleOwner());
                        }
                    });
                }, 900);
            }
        });

        return view;
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

    private boolean validPassword(){
        if (!Objects.equals(passwordEditText.getText().toString(), "")){
            return true;
        }
        passwordEditText.setError(getString(R.string.required));
        return false;
    }

    private boolean validNewPassword(){
        if (!Objects.equals(newPasswordEditText.getText().toString(), "")){
            return true;
        }
        newPasswordEditText.setError(getString(R.string.required));
        return false;
    }

    private boolean validConfirmPassword(){
        if (!Objects.equals(confirmPasswordEditText.getText().toString(), "")){
            return true;
        }
        confirmPasswordEditText.setError(getString(R.string.required));
        return false;
    }

    private boolean validPasswordMatch(){
        if (Objects.equals(newPasswordEditText.getText().toString(), confirmPasswordEditText.getText().toString())){
            return true;
        }
        newPasswordEditText.setError(getString(R.string.must_be_same));
        confirmPasswordEditText.setError(getString(R.string.must_be_same));
        return false;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        if (dialog.getWindow() != null) {
            dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

}
