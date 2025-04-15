package com.ftalaveram.habbbits.presentation.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ftalaveram.habbbits.repositories.api.ApiClient;
import com.ftalaveram.habbbits.repositories.api.ApiService;
import com.ftalaveram.habbbits.repositories.api.RemoteDataSource;
import com.ftalaveram.habbbits.repositories.models.Achievements;
import com.ftalaveram.habbbits.repositories.models.UserHabit;
import com.ftalaveram.habbbits.repositories.repository.AchievementsRepository;
import com.ftalaveram.habbbits.session.SessionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AchievementsViewModel extends AndroidViewModel {

    ApiService apiService = ApiClient.getApiService();
    RemoteDataSource remoteDataSource = new RemoteDataSource(apiService);
    AchievementsRepository repository = new AchievementsRepository(remoteDataSource);
    private SessionManager sessionManager;

    private final MutableLiveData<List<Achievements>> achievementsLiveData = new MutableLiveData<>();

    public AchievementsViewModel(@NonNull Application application){
        super(application);
        sessionManager = new SessionManager(application.getApplicationContext());
        loadAchievements();
    }

    public LiveData<List<Achievements>> getAchievements() {
        return achievementsLiveData;
    }

    private void loadAchievements(){
        repository.getAchievements("Bearer " + sessionManager.getToken(), new Callback<List<Achievements>>() {
            @Override
            public void onResponse(Call<List<Achievements>> call, Response<List<Achievements>> response) {
                if (response.body() != null && response.isSuccessful()){
                    achievementsLiveData.postValue(response.body());
                }
                else{
                    achievementsLiveData.postValue(new ArrayList<>());
                }
            }

            @Override
            public void onFailure(Call<List<Achievements>> call, Throwable t) {
                achievementsLiveData.postValue(new ArrayList<>());
            }
        });
    }

    public int getTotalPoints(){

        int puntuacion = 0;

        if (achievementsLiveData.getValue() != null){
            for ( Achievements achievement : achievementsLiveData.getValue()){
                puntuacion += achievement.getPuntuacion();
            }
        }

        return puntuacion;
    }
}
