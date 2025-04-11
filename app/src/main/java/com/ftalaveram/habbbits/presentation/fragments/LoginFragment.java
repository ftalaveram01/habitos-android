package com.ftalaveram.habbbits.presentation.fragments;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.transition.Visibility;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ftalaveram.habbbits.R;
import com.ftalaveram.habbbits.databinding.FragmentLoginBinding;
import com.ftalaveram.habbbits.presentation.activities.HomeActivity;
import com.ftalaveram.habbbits.presentation.viewmodels.LoginViewModel;
import com.ftalaveram.habbbits.repositories.api.ApiClient;
import com.ftalaveram.habbbits.repositories.api.ApiService;
import com.ftalaveram.habbbits.repositories.api.RemoteDataSource;
import com.ftalaveram.habbbits.repositories.models.LoginData;
import com.ftalaveram.habbbits.repositories.models.LoginRequest;
import com.ftalaveram.habbbits.repositories.repository.UserRepository;
import com.ftalaveram.habbbits.session.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    FragmentLoginBinding binding;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        LoginViewModel loginViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginViewModel.login(binding.usernameInput.getText().toString(), binding.passwordInput.getText().toString());
            }
        });

        loginViewModel.loginData.observe(getViewLifecycleOwner(), new Observer<LoginData>() {
            @Override
            public void onChanged(LoginData loginData) {
                if (loginData.isSuccess()){
                    startActivity(new Intent(requireActivity(), HomeActivity.class));
                    requireActivity().finish();
                }else{
                    Log.d("ERROR DE CREDENCIALES", "El usuario o contraseña no son correctos");
                }
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}