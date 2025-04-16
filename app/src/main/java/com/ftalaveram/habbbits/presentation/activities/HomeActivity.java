package com.ftalaveram.habbbits.presentation.activities;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.ftalaveram.habbbits.R;
import com.ftalaveram.habbbits.databinding.ActivityHomeBinding;

import java.util.Objects;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding binding;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((binding = ActivityHomeBinding.inflate(getLayoutInflater())).getRoot());
        setContentView(binding.getRoot());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setSupportActionBar(binding.toolbar);

        NavController navController = ((NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment)).getNavController();

        //sirve para poner el tinte de los iconos del bottom sin tinte
        binding.bottomNavView.setItemIconTintList(null);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.myHabitsFragment, R.id.recommendedFragment, R.id.achievementsFragment, R.id.profileFragment
        ).build();

        NavigationUI.setupWithNavController(binding.bottomNavView, navController);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        TextView toolbarTitle = binding.toolbar.findViewById(R.id.toolbar_title);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            int id = destination.getId();
            if (id == R.id.myHabitsFragment) {
                toolbarTitle.setText(R.string.my_habbbits);
            } else if (id == R.id.recommendedFragment) {
                toolbarTitle.setText(R.string.recommended);
            } else if (id == R.id.achievementsFragment) {
                toolbarTitle.setText(R.string.achievements);
            } else if (id == R.id.profileFragment) {
                toolbarTitle.setText(R.string.profile);
            }
        });
    }
}