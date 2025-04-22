package com.ftalaveram.habbbits.presentation.adapters;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.ftalaveram.habbbits.R;
import com.ftalaveram.habbbits.databinding.ViewholderHabitoBinding;
import com.ftalaveram.habbbits.presentation.viewholders.MyHabbbitsViewHolder;
import com.ftalaveram.habbbits.repositories.api.ApiClient;
import com.ftalaveram.habbbits.repositories.api.ApiService;
import com.ftalaveram.habbbits.repositories.api.RemoteDataSource;
import com.ftalaveram.habbbits.repositories.models.DoneResponse;
import com.ftalaveram.habbbits.repositories.models.UserHabit;
import com.ftalaveram.habbbits.repositories.repository.AchievementsRepository;
import com.ftalaveram.habbbits.session.SessionManager;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyHabbbitsRecycledViewAdapter extends RecyclerView.Adapter<MyHabbbitsViewHolder> {

    private List<UserHabit> myHabbbits;

    ApiService apiService = ApiClient.getApiService();
    RemoteDataSource remoteDataSource = new RemoteDataSource(apiService);
    AchievementsRepository repository = new AchievementsRepository(remoteDataSource);
    private final SessionManager sessionManager;

    public MyHabbbitsRecycledViewAdapter(Application application) {
        sessionManager = new SessionManager(application.getApplicationContext());
    }

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

        Log.d("FECHA DE MY HABBBIT", userHabit.getFechaNuevaActualizacion().toString());

        Long diferenciaMs = userHabit.getFechaNuevaActualizacion().getTime() - new Date().getTime();

        if (diferenciaMs <= 0){
            holder.getBinding().timeLeft.setText(R.string.too_late);
        }else{
            int dias = (int) Math.floor((double) diferenciaMs / (1000 * 60 * 60 * 24));
            int horas = (int) Math.floor((double) (diferenciaMs % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));

            holder.getBinding().timeLeft.setText("Days: " + dias + ", Hours: " + horas);
        }
        //TODO: poner las resta de las fechas

        holder.getBinding().btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repository.habitDone("Bearer " + sessionManager.getToken(), userHabit.getId(), new Callback<DoneResponse>() {
                    @Override
                    public void onResponse(Call<DoneResponse> call, Response<DoneResponse> response) {
                        if (response.body() != null && response.isSuccessful()){
                            Toast.makeText(v.getContext(), "HABIT DONE", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<DoneResponse> call, Throwable t) {

                    }
                });
            }
        });

        holder.getBinding().btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putBoolean("isUpdate", true);
                args.putLong("id", userHabit.getId());
                args.putString("name", userHabit.getNombre());
                args.putString("description", userHabit.getDescripcion());
                args.putBoolean("isPublic", userHabit.isPublico());
                Navigation.findNavController(holder.getBinding().getRoot()).navigate(R.id.action_myHabitsFragment_to_createHabitFragment, args);
            }
        });
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