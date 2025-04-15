package com.ftalaveram.habbbits.presentation.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ftalaveram.habbbits.R;
import com.ftalaveram.habbbits.databinding.ViewholderAchievementsBinding;
import com.ftalaveram.habbbits.databinding.ViewholderHabitoBinding;
import com.ftalaveram.habbbits.presentation.viewholders.AchievementsViewHolder;
import com.ftalaveram.habbbits.presentation.viewholders.MyHabbbitsViewHolder;
import com.ftalaveram.habbbits.repositories.models.Achievements;
import com.ftalaveram.habbbits.repositories.models.UserHabit;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
        Date fecha = achievement.getFechaRegistro();
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);

        int dia = cal.get(Calendar.DAY_OF_MONTH);
        int mes = cal.get(Calendar.MONTH) + 1; // ¡Los meses van de 0 a 11!
        int anio = cal.get(Calendar.YEAR);
        int hora = cal.get(Calendar.HOUR_OF_DAY);
        int minuto = cal.get(Calendar.MINUTE);

        String fechaFormateada = String.format(Locale.getDefault(), "%02d/%02d/%04d, %02d:%02d", dia, mes, anio, hora, minuto);

        holder.getBinding().name.setText(achievement.getNombre());
        holder.getBinding().date.setText(fechaFormateada);
        holder.getBinding().points.setText(String.valueOf(achievement.getPuntuacion()));
        if (!achievement.isPuntual()){
            holder.getBinding().puntual.setBackgroundResource(R.drawable.textview_impuntual);
        }

    }

    @Override
    public int getItemCount() {
        return achievements != null ? achievements.size() : 0;
    }

    public void setAchievements(List<Achievements> achievements){
        this.achievements = achievements;
        notifyDataSetChanged();
    }
}
