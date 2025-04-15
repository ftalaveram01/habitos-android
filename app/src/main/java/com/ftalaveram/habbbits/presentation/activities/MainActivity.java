package com.ftalaveram.habbbits.presentation.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ftalaveram.habbbits.R;
import com.ftalaveram.habbbits.databinding.ActivityMainBinding;
import com.ftalaveram.habbbits.presentation.viewmodels.LoginViewModel;
import com.ftalaveram.habbbits.presentation.viewmodels.VerifyAccessViewModel;
import com.ftalaveram.habbbits.repositories.models.VerifyAccess;
import com.ftalaveram.habbbits.session.SessionManager;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private SessionManager sessionManager;



    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((binding = ActivityMainBinding.inflate(getLayoutInflater())).getRoot());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        VerifyAccessViewModel verifyAccessViewModel = new ViewModelProvider(MainActivity.this).get(VerifyAccessViewModel.class);

        verifyAccessViewModel.verifyAccess();

        verifyAccessViewModel.verifyAccess.observe(this, new Observer<VerifyAccess>() {
            @Override
            public void onChanged(VerifyAccess verifyAccess) {
                if (verifyAccess.isAuthenticated()){
                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
                    MainActivity.this.finish();
                }else {
                    startActivity(new Intent(MainActivity.this, AuthActivity.class));
                    MainActivity.this.finish();
                }
            }
        });
    }
}