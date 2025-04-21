package com.ftalaveram.habbbits.presentation.fragments;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.ftalaveram.habbbits.R;
import com.ftalaveram.habbbits.databinding.FragmentAchievementsBinding;
import com.ftalaveram.habbbits.presentation.adapters.AchievementsRecycledViewAdapter;
import com.ftalaveram.habbbits.presentation.viewmodels.AchievementsViewModel;
import com.ftalaveram.habbbits.repositories.models.Achievements;

import java.util.ArrayList;
import java.util.List;


public class AchievementsFragment extends Fragment {

    private FragmentAchievementsBinding binding;
    private AchievementsRecycledViewAdapter recycledViewAdapter;
    private AchievementsViewModel achievementsViewModel;

    private ArrayAdapter<String> spinnerAdapter;
    private List<String> uniqueNames = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        achievementsViewModel = new ViewModelProvider(this).get(AchievementsViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAchievementsBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int puntuacion = 0;

        recycledViewAdapter = new AchievementsRecycledViewAdapter();
        GridLayoutManager grid = new GridLayoutManager(getContext(), 1);
        binding.recyclerViewAchievements.setLayoutManager(grid);
        binding.recyclerViewAchievements.setAdapter(recycledViewAdapter);


        achievementsViewModel.getAchievements().observe(getViewLifecycleOwner(), achievements -> {
            recycledViewAdapter.setAchievements(achievements);
            recycledViewAdapter.notifyDataSetChanged();
            setupSpinner();
            binding.puntuacionTotal.setText(getString(R.string.total_points) + String.valueOf(achievementsViewModel.getTotalPoints()));

            if(recycledViewAdapter.getItemCount() > 0){
                binding.textoVacioAchievements.setVisibility(GONE);
            }else{
                binding.textoVacioAchievements.setVisibility(VISIBLE);
            }
        });

        if(recycledViewAdapter.getItemCount() > 0){
            binding.textoVacioAchievements.setVisibility(GONE);
        }else{
            binding.textoVacioAchievements.setVisibility(VISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        achievementsViewModel.rechargeAchievements();
    }

    private void setupSpinner(){
        uniqueNames.add(getString(R.string.all));

        for (Achievements a : achievementsViewModel.getAchievements().getValue()){
            if (!uniqueNames.contains(a.getNombre())){
                uniqueNames.add(a.getNombre());
            }
        }

        spinnerAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                uniqueNames
        );
        spinnerAdapter.setDropDownViewResource(R.layout.custom_spinner_item);
        binding.mySpinner.setAdapter(spinnerAdapter);

        binding.mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                recycledViewAdapter.setAchievements(achievementsViewModel.filterBy(uniqueNames.get(position)));
                recycledViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }
}