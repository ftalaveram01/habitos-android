package com.ftalaveram.habbbits.presentation.viewmodels;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ftalaveram.habbbits.R;
import com.ftalaveram.habbbits.repositories.api.ApiClient;
import com.ftalaveram.habbbits.repositories.api.ApiService;
import com.ftalaveram.habbbits.repositories.api.RemoteDataSource;
import com.ftalaveram.habbbits.repositories.models.UserHabit;
import com.ftalaveram.habbbits.repositories.repository.HabitRepository;
import com.ftalaveram.habbbits.session.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyHabitsViewModel extends AndroidViewModel {

    ApiService apiService = ApiClient.getApiService();
    RemoteDataSource remoteDataSource = new RemoteDataSource(apiService);
    HabitRepository repository = new HabitRepository(remoteDataSource);
    private final SessionManager sessionManager;
    private Long habitId;

    public final MutableLiveData<Boolean> deleteLiveData = new MutableLiveData<>();

    public final MutableLiveData<List<UserHabit>> habitsLiveData = new MutableLiveData<>();

    public MyHabitsViewModel(@NonNull Application application) {
        super(application);
        sessionManager = new SessionManager(application.getApplicationContext());
    }

    public LiveData<List<UserHabit>> getHabits() {
        return habitsLiveData;
    }

    private void loadHabits() {
        repository.getUserHabits("Bearer " + sessionManager.getToken(), new Callback<List<UserHabit>>() {
            @Override
            public void onResponse(@NonNull Call<List<UserHabit>> call, @NonNull Response<List<UserHabit>> response) {
                if (response.body() != null && response.isSuccessful()){
                    habitsLiveData.postValue(response.body());
                }
                else{
                    habitsLiveData.postValue(new ArrayList<>());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<UserHabit>> call, @NonNull Throwable t) {
                habitsLiveData.postValue(new ArrayList<>());
                Toast.makeText(getApplication().getApplicationContext(), R.string.no_internet, Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void rechargeHabits(){
        loadHabits();
    }

    public void deleteHabit(Long id){
        repository.deleteHabit("Bearer " + sessionManager.getToken(), id, new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    loadHabits();
                    deleteLiveData.postValue(true);
                } else {
                    deleteLiveData.postValue(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                deleteLiveData.postValue(false);
                Toast.makeText(getApplication().getApplicationContext(), R.string.no_internet, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public Long getHabitId() {
        return habitId;
    }

    public void setHabitId(Long habitId) {
        this.habitId = habitId;
    }
}