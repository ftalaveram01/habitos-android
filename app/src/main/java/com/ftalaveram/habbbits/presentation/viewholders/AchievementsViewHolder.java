package com.ftalaveram.habbbits.presentation.viewholders;

import androidx.recyclerview.widget.RecyclerView;

import com.ftalaveram.habbbits.databinding.ViewholderAchievementsBinding;

public class AchievementsViewHolder extends RecyclerView.ViewHolder {

    public final ViewholderAchievementsBinding binding;

    public AchievementsViewHolder(ViewholderAchievementsBinding binding){
        super(binding.getRoot());
        this.binding = binding;
    }

    public ViewholderAchievementsBinding getBinding(){
        return this.binding;
    }
}
