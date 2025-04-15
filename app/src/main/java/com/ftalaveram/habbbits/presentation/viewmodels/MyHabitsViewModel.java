package com.ftalaveram.habbbits.presentation.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ftalaveram.habbbits.repositories.api.ApiClient;
import com.ftalaveram.habbbits.repositories.api.ApiService;
import com.ftalaveram.habbbits.repositories.api.RemoteDataSource;
import com.ftalaveram.habbbits.repositories.models.UserHabit;
import com.ftalaveram.habbbits.repositories.repository.HabitRepository;
import com.ftalaveram.habbbits.session.SessionManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyHabitsViewModel extends AndroidViewModel {

    ApiService apiService = ApiClient.getApiService();
    RemoteDataSource remoteDataSource = new RemoteDataSource(apiService);
    HabitRepository repository = new HabitRepository(remoteDataSource);
    private SessionManager sessionManager;

    private final MutableLiveData<List<UserHabit>> habitsLiveData = new MutableLiveData<>();

    public MyHabitsViewModel(@NonNull Application application) {
        super(application);
        sessionManager = new SessionManager(application.getApplicationContext());
        loadHabits();
    }

    public LiveData<List<UserHabit>> getHabits() {
        return habitsLiveData;
    }

    private void loadHabits() {
        repository.getUserHabits("Bearer " + sessionManager.getToken(), new Callback<List<UserHabit>>() {
            @Override
            public void onResponse(Call<List<UserHabit>> call, Response<List<UserHabit>> response) {
                if (response.body() != null && response.isSuccessful()){
                    habitsLiveData.postValue(response.body());
                }
                else{
                    Log.d("DEBUG", "Ha saltado el else");
                    habitsLiveData.postValue(new ArrayList<>());
                }
            }

            @Override
            public void onFailure(Call<List<UserHabit>> call, Throwable t) {
                Log.d("DEBUG", "Ha saltado on failure: " + t.toString());
                habitsLiveData.postValue(new ArrayList<>());
            }
        });

    }

    public void addHabit(UserHabit habit) {
        List<UserHabit> currentHabits = habitsLiveData.getValue();
        if (currentHabits == null) {
            currentHabits = new ArrayList<>();
        }
        currentHabits.add(habit);
        habitsLiveData.postValue(currentHabits);
    }
}