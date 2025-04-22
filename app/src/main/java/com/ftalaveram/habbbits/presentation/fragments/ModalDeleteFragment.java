package com.ftalaveram.habbbits.presentation.fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ftalaveram.habbbits.R;
import com.ftalaveram.habbbits.presentation.viewmodels.MyHabitsViewModel;

public class ModalDeleteFragment extends DialogFragment {

    private MyHabitsViewModel myHabitsViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myHabitsViewModel = new ViewModelProvider(requireActivity()).get(MyHabitsViewModel.class);
        Bundle args = getArguments();

        if (args != null){
            myHabitsViewModel.setHabitId(args.getLong("id", -1));
        }

        View view = inflater.inflate(R.layout.modal_delete, container, true);

        view.findViewById(R.id.btn_close_modal).setOnClickListener(v -> {
            dismiss();
        });

        view.findViewById(R.id.btn_confirm_delete).setOnClickListener(v -> {

            if (myHabitsViewModel.getHabitId() != -1)
                myHabitsViewModel.deleteHabit(myHabitsViewModel.getHabitId());

            myHabitsViewModel.deleteLiveData.observe(getViewLifecycleOwner(), success -> {
                if (success != null) {
                    if (success) {
                        Toast.makeText(requireContext(), "Hábito eliminado", Toast.LENGTH_SHORT).show();
                    }
                    dismiss();
                    myHabitsViewModel.deleteLiveData.removeObservers(getViewLifecycleOwner());
                }
            });

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
