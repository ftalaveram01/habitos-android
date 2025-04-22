package com.ftalaveram.habbbits.presentation.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.ftalaveram.habbbits.R;

public class ModalDeleteFragment extends DialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflar el layout del modal
        View view = inflater.inflate(R.layout.modal_delete, container, false);

        // Configurar elementos del modal (ejemplo: botón de cerrar)
        view.findViewById(R.id.btnCloseModal).setOnClickListener(v -> {
            dismiss();
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Ajustar el ancho del modal (opcional)
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }
}
