package com.ftalaveram.habbbits.presentation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.ftalaveram.habbbits.databinding.ActivityMainBinding;
import com.ftalaveram.habbbits.session.SessionManager;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((binding = ActivityMainBinding.inflate(getLayoutInflater())).getRoot());
        sessionManager = new SessionManager(this);

        new Handler().postDelayed(() -> {
            if (sessionManager.isLoggedIn()) {
                startActivity(new Intent(this, HomeActivity.class));
            } else {
                startActivity(new Intent(this, AuthActivity.class));
            }
            finish();
        }, 200);
    }
}