package com.ftalaveram.habbbits.presentation.fragments;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import com.ftalaveram.habbbits.R;
import com.ftalaveram.habbbits.databinding.FragmentMyHabitsBinding;
import com.ftalaveram.habbbits.presentation.adapters.MyHabbbitsRecycledViewAdapter;
import com.ftalaveram.habbbits.presentation.viewmodels.MyHabitsViewModel;

public class MyHabitsFragment extends Fragment {

    private FragmentMyHabitsBinding binding;
    private MyHabbbitsRecycledViewAdapter recycledViewAdapter;
    private MyHabitsViewModel myHabitsViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myHabitsViewModel = new ViewModelProvider(this).get(MyHabitsViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMyHabitsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Configurar RecyclerView
        recycledViewAdapter = new MyHabbbitsRecycledViewAdapter(this.getActivity().getApplication());
        GridLayoutManager grid = new GridLayoutManager(getContext(), 1);
        binding.recyclerViewMyHabbbits.setLayoutManager(grid);
        binding.recyclerViewMyHabbbits.setAdapter(recycledViewAdapter);

        // Observar cambios en los datos
        myHabitsViewModel.getHabits().observe(getViewLifecycleOwner(), habits -> {
            recycledViewAdapter.setMyHabbbits(habits);
            recycledViewAdapter.notifyDataSetChanged();

            if(recycledViewAdapter.getItemCount() > 0){
                binding.textoVacioMyHabbbits.setVisibility(GONE);
            }else{
                binding.textoVacioMyHabbbits.setVisibility(VISIBLE);
            }
        });

        if(recycledViewAdapter.getItemCount() > 0){
            binding.textoVacioMyHabbbits.setVisibility(GONE);
        }else{
            binding.textoVacioMyHabbbits.setVisibility(VISIBLE);
        }

        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(MyHabitsFragment.this.getView()).navigate(R.id.action_myHabitsFragment_to_createHabitFragment);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        myHabitsViewModel.rechargeHabits();
    }
}