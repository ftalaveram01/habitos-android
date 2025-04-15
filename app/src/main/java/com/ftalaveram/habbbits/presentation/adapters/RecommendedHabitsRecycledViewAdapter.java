package com.ftalaveram.habbbits.presentation.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ftalaveram.habbbits.databinding.ViewholderRecommendedBinding;
import com.ftalaveram.habbbits.presentation.viewholders.RecommendedHabitsViewHolder;
import com.ftalaveram.habbbits.repositories.models.Habit;

import java.util.List;

public class RecommendedHabitsRecycledViewAdapter extends RecyclerView.Adapter<RecommendedHabitsViewHolder> {

    private List<Habit> recommendedHabits;


    @NonNull
    @Override
    public RecommendedHabitsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewholderRecommendedBinding binding = ViewholderRecommendedBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new RecommendedHabitsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendedHabitsViewHolder holder, int position) {
        Habit habit = recommendedHabits.get(position);
        holder.getBinding().title.setText(habit.getNombre());
        holder.getBinding().description.setText(habit.getDescripcion());
    }

    @Override
    public int getItemCount() {
        return recommendedHabits != null ? recommendedHabits.size() : 0;
    }

    public void setMyHabbbits(List<Habit> recommendedHabits) {
        this.recommendedHabits = recommendedHabits;
        notifyDataSetChanged();
    }
}
