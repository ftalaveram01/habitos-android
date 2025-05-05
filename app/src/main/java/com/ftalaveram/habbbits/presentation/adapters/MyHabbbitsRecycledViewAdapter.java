package com.ftalaveram.habbbits.presentation.adapters;

import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.ftalaveram.habbbits.R;
import com.ftalaveram.habbbits.databinding.ViewholderHabitoBinding;
import com.ftalaveram.habbbits.presentation.fragments.ModalDeleteFragment;
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
    private final FragmentManager fragmentManager;

    public MyHabbbitsRecycledViewAdapter(Application application, FragmentManager fragmentManager) {
        sessionManager = new SessionManager(application.getApplicationContext());
        this.fragmentManager = fragmentManager;
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

        long diferenciaMs = userHabit.getFechaNuevaActualizacion().getTime() - new Date().getTime();

        if (diferenciaMs <= 0){
            holder.getBinding().timeLeft.setText(R.string.too_late);
        }else{
            int dias = (int) Math.floor((double) diferenciaMs / (1000 * 60 * 60 * 24));
            int horas = (int) Math.floor((double) (diferenciaMs % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));

            holder.getBinding().timeLeft.setText(holder.getBinding().getRoot().getContext().getString(R.string.time_left, String.valueOf(dias), String.valueOf(horas)));
        }

        holder.getBinding().btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repository.habitDone("Bearer " + sessionManager.getToken(), userHabit.getId(), new Callback<DoneResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<DoneResponse> call, @NonNull Response<DoneResponse> response) {
                        if (response.body() != null && response.isSuccessful()){
                            mostrarDialogCompleto(userHabit.getNombre(), v);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<DoneResponse> call, @NonNull Throwable t) {

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

        holder.getBinding().btnDelete.setOnClickListener(v -> {
            ModalDeleteFragment modal = new ModalDeleteFragment();

            Bundle args = new Bundle();
            args.putLong("id", userHabit.getId());
            modal.setArguments(args);

            modal.show(fragmentManager, "ModalDelete");
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

    private void mostrarDialogCompleto(String nombreHabito, View view) {
        Context context = view.getContext();

        Dialog dialog = new Dialog(context, R.style.DialogNoTitle);
        dialog.setContentView(R.layout.dialog_habito_completado);

        TextView tvTitulo = dialog.findViewById(R.id.tvTitulo);
        ImageView ivIcono = dialog.findViewById(R.id.ivIcono);
        Button btnAceptar = dialog.findViewById(R.id.btnAceptar);

        String mensaje = String.format("¡%s completado!", nombreHabito);
        tvTitulo.setText(mensaje);

        Animation anim = AnimationUtils.loadAnimation(context, R.anim.bounce);
        ivIcono.startAnimation(anim);

        btnAceptar.setOnClickListener(v -> dialog.dismiss());

        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        dialog.show();
    }
}