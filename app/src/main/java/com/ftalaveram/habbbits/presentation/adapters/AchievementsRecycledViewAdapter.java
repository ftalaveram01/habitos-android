package com.ftalaveram.habbbits.presentation.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ftalaveram.habbbits.databinding.ViewholderAchievementsBinding;
import com.ftalaveram.habbbits.databinding.ViewholderHabitoBinding;
import com.ftalaveram.habbbits.presentation.viewholders.AchievementsViewHolder;
import com.ftalaveram.habbbits.presentation.viewholders.MyHabbbitsViewHolder;
import com.ftalaveram.habbbits.repositories.models.Achievements;
import com.ftalaveram.habbbits.repositories.models.UserHabit;

import java.util.List;

public class AchievementsRecycledViewAdapter extends RecyclerView.Adapter<AchievementsViewHolder> {

    private List<Achievements> achievements;

    @NonNull
    @Override
    public AchievementsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewholderAchievementsBinding binding = ViewholderAchievementsBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new AchievementsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AchievementsViewHolder holder, int position) {
        Achievements achievement = achievements.get(position);
        holder.getBinding().title.setText(achievement.getNombre());
    }

    @Override
    public int getItemCount() {
        return achievements != null ? achievements.size() : 0;
    }
}
