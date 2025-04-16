package com.ftalaveram.habbbits.presentation.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ftalaveram.habbbits.R;

public class InicioFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inicio, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Botón para ir a Login
        view.findViewById(R.id.btn_go_to_login).setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_inicioFragment_to_loginFragment);
        });

        // Botón para ir a Register
        view.findViewById(R.id.btn_go_to_register).setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_inicioFragment_to_registerFragment);
        });

    }
}