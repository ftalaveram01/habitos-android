package com.ftalaveram.habbbits.presentation.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.ftalaveram.habbbits.databinding.ViewholderHabitoBinding;
import com.ftalaveram.habbbits.presentation.viewholders.MyHabbbitsViewHolder;
import com.ftalaveram.habbbits.repositories.models.UserHabit;

import java.util.List;

public class MyHabbbitsRecycledViewAdapter extends RecyclerView.Adapter<MyHabbbitsViewHolder> {

    private List<UserHabit> myHabbbits;

    @NonNull
    @Override
    public MyHabbbitsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewholderHabitoBinding binding = ViewholderHabitoBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new MyHabbbitsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHabbbitsViewHolder holder, int position) {
        UserHabit userHabit = myHabbbits.get(position);
        holder.getBinding().title.setText(userHabit.getNombre());
        holder.getBinding().description.setText(userHabit.getDescripcion());
    }

    @Override
    public int getItemCount() {
        return myHabbbits != null ? myHabbbits.size() : 0;
    }

    public void setMyHabbbits(List<UserHabit> myHabbbits) {
        this.myHabbbits = myHabbbits;
        notifyDataSetChanged();
    }
}