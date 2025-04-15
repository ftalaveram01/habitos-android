package com.ftalaveram.habbbits.presentation.fragments;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ftalaveram.habbbits.R;
import com.ftalaveram.habbbits.databinding.FragmentRecommendedBinding;
import com.ftalaveram.habbbits.presentation.adapters.MyHabbbitsRecycledViewAdapter;
import com.ftalaveram.habbbits.presentation.adapters.RecommendedHabitsRecycledViewAdapter;
import com.ftalaveram.habbbits.presentation.viewmodels.RecommendedHabitsViewModel;

public class RecommendedFragment extends Fragment {

    private FragmentRecommendedBinding binding;

    private RecommendedHabitsRecycledViewAdapter recycledViewAdapter;

    private RecommendedHabitsViewModel recommendedHabitsViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recommendedHabitsViewModel = new ViewModelProvider(this).get(RecommendedHabitsViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRecommendedBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Configurar RecyclerView
        recycledViewAdapter = new RecommendedHabitsRecycledViewAdapter();
        GridLayoutManager grid = new GridLayoutManager(getContext(), 1);
        binding.recyclerViewRecommendedHabits.setLayoutManager(grid);
        binding.recyclerViewRecommendedHabits.setAdapter(recycledViewAdapter);

        // Observar cambios en los datos
        recommendedHabitsViewModel.getHabits().observe(getViewLifecycleOwner(), habits -> {
            recycledViewAdapter.setMyHabbbits(habits);
            recycledViewAdapter.notifyDataSetChanged();
            if(recycledViewAdapter.getItemCount() > 0){
                binding.textoVacioRecommendedHabits.setVisibility(GONE);
            }else{
                binding.textoVacioRecommendedHabits.setVisibility(VISIBLE);
            }
        });

        if(recycledViewAdapter.getItemCount() > 0){
            binding.textoVacioRecommendedHabits.setVisibility(GONE);
        }else{
            binding.textoVacioRecommendedHabits.setVisibility(VISIBLE);
        }
    }
}