package com.ftalaveram.habbbits.presentation.fragments;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ftalaveram.habbbits.R;
import com.ftalaveram.habbbits.presentation.viewmodels.MyHabitsViewModel;

public class ModalDeleteFragment extends DialogFragment {

    private MyHabitsViewModel myHabitsViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myHabitsViewModel = new ViewModelProvider(requireActivity()).get(MyHabitsViewModel.class);
        Bundle args = getArguments();
        if (args != null){
            myHabitsViewModel.setHabitId(args.getLong("id", -1));
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.modal_delete, container, true);

        view.findViewById(R.id.btn_close_modal).setOnClickListener(v -> dismiss());

        view.findViewById(R.id.btn_confirm_delete).setOnClickListener(v -> {

            if (myHabitsViewModel.getHabitId() != -1)
                myHabitsViewModel.deleteHabit(myHabitsViewModel.getHabitId());

            myHabitsViewModel.deleteLiveData.observe(getViewLifecycleOwner(), success -> {
                if (success != null) {
                    if (success) {
                        mostrarDialogCompleto(getString(R.string.deleted), v);
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
}
