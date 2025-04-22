package com.ftalaveram.habbbits.presentation.fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.ftalaveram.habbbits.R;
import com.ftalaveram.habbbits.presentation.viewmodels.DeleteDialogViewModel;

public class ModalDeleteFragment extends DialogFragment {

    private DeleteDialogViewModel deleteDialogViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        deleteDialogViewModel = new ViewModelProvider(this).get(DeleteDialogViewModel.class);

        View view = inflater.inflate(R.layout.modal_delete, container, true);

        view.findViewById(R.id.btn_close_modal).setOnClickListener(v -> {
            dismiss();
        });

        view.findViewById(R.id.btn_delete).setOnClickListener(v -> {
            //deleteDialogViewModel.deleteHabit();
        });

        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        // Solicitar ventana sin título
        if (dialog.getWindow() != null) {
            dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Ajustar el ancho del modal (opcional)
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }
}
