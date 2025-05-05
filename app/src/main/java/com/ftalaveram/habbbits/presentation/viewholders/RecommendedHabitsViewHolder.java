package com.ftalaveram.habbbits.presentation.viewholders;

import androidx.recyclerview.widget.RecyclerView;

import com.ftalaveram.habbbits.databinding.ViewholderRecommendedBinding;

public class RecommendedHabitsViewHolder extends RecyclerView.ViewHolder {

    public final ViewholderRecommendedBinding binding;
    public RecommendedHabitsViewHolder(ViewholderRecommendedBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public ViewholderRecommendedBinding getBinding(){
        return this.binding;
    }
}
