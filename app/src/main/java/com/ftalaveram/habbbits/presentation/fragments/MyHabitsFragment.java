package com.ftalaveram.habbbits.presentation.fragments;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
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
        myHabitsViewModel = new ViewModelProvider(requireActivity()).get(MyHabitsViewModel.class);
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
        recycledViewAdapter = new MyHabbbitsRecycledViewAdapter(this.getActivity().getApplication(), getChildFragmentManager());
        GridLayoutManager grid = new GridLayoutManager(getContext(), 1);
        binding.recyclerViewMyHabbbits.setLayoutManager(grid);
        binding.recyclerViewMyHabbbits.setAdapter(recycledViewAdapter);

        // Observar cambios en los datos
        myHabitsViewModel.habitsLiveData.observe(getViewLifecycleOwner(), habits -> {
            Log.e("RECARGANDO...", "");
            recycledViewAdapter.setMyHabbbits(habits);

            if(recycledViewAdapter.getItemCount() > 0){
                binding.textoVacioMyHabbbits.setVisibility(GONE);
            }else{
                binding.textoVacioMyHabbbits.setVisibility(VISIBLE);
            }
        });

        myHabitsViewModel.deleteLiveData.observe(getViewLifecycleOwner(), success -> {
            Log.e("VALOR DE SUCCESS", success.toString());
            if (success != null) {
                if (success) {
                    Log.d("DEBUG EN FRAGMENT", "SE HA LLEGADO A DESPUES DEL RECHARGE");
                }
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