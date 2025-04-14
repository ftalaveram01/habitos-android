package com.ftalaveram.habbbits.presentation.viewholders;

import androidx.recyclerview.widget.RecyclerView;

import com.ftalaveram.habbbits.databinding.ViewholderHabitoBinding;

public class MyHabbbitsViewHolder extends RecyclerView.ViewHolder {

    public final ViewholderHabitoBinding binding;

    public MyHabbbitsViewHolder(ViewholderHabitoBinding binding){
        super(binding.getRoot());
        this.binding = binding;
    }

    public ViewholderHabitoBinding getBinding(){
        return this.binding;
    }
}
