package com.ftalaveram.habbbits.presentation.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.ftalaveram.habbbits.repositories.api.ApiClient;
import com.ftalaveram.habbbits.repositories.api.ApiService;
import com.ftalaveram.habbbits.repositories.api.RemoteDataSource;
import com.ftalaveram.habbbits.repositories.models.CreateRequest;
import com.ftalaveram.habbbits.repositories.models.CreateResponse;
import com.ftalaveram.habbbits.repositories.models.UpdateRequest;
import com.ftalaveram.habbbits.repositories.models.UpdateResponse;
import com.ftalaveram.habbbits.repositories.repository.HabitRepository;
import com.ftalaveram.habbbits.session.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateHabitsViewModel extends AndroidViewModel {

    ApiService apiService = ApiClient.getApiService();
    RemoteDataSource remoteDataSource = new RemoteDataSource(apiService);
    HabitRepository repository = new HabitRepository(remoteDataSource);
    private SessionManager sessionManager;

    public final MutableLiveData<CreateResponse> createLiveData = new MutableLiveData<>();
    public final MutableLiveData<UpdateResponse> updateLiveData = new MutableLiveData<>();

    public CreateHabitsViewModel(@NonNull Application application){
        super(application);
        sessionManager = new SessionManager(application.getApplicationContext());
    }

    public void createHabit(String nombre, String descripcion, String creadoEn, String fechaInicio, int horasIntervalo, boolean publico){
        CreateRequest request = new CreateRequest(nombre, descripcion, creadoEn, fechaInicio, horasIntervalo, publico);

        repository.createHabit("Bearer " + sessionManager.getToken(), request, new Callback<CreateResponse>() {
            @Override
            public void onResponse(Call<CreateResponse> call, Response<CreateResponse> response) {
                if (response.isSuccessful() && response.body() != null){
                    createLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<CreateResponse> call, Throwable t) {

            }
        });
    }

    public void updateHabit(String nombre, String descripcion, boolean publico, Long id){
        UpdateRequest request = new UpdateRequest(nombre, descripcion, publico);

        repository.updateHabit("Bearer " + sessionManager.getToken(), request, id, new Callback<UpdateResponse>() {
            @Override
            public void onResponse(Call<UpdateResponse> call, Response<UpdateResponse> response) {
                if (response.isSuccessful() && response.body() != null){
                    updateLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<UpdateResponse> call, Throwable t) {

            }
        });
    }
}
