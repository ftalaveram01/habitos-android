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
import com.ftalaveram.habbbits.repositories.models.Habit;
import com.ftalaveram.habbbits.repositories.repository.HabitRepository;
import com.ftalaveram.habbbits.session.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecommendedHabitsViewModel extends AndroidViewModel {

    ApiService apiService = ApiClient.getApiService();
    RemoteDataSource remoteDataSource = new RemoteDataSource(apiService);
    HabitRepository repository = new HabitRepository(remoteDataSource);
    private SessionManager sessionManager;

    private final MutableLiveData<List<Habit>> habitsLiveData = new MutableLiveData<>();

    public RecommendedHabitsViewModel(@NonNull Application application){
        super(application);
    }

    public LiveData<List<Habit>> getHabits() {
        return habitsLiveData;
    }

    private void loadHabits(){
        repository.getRecommendedHabits(new Callback<List<Habit>>() {
            @Override
            public void onResponse(@NonNull Call<List<Habit>> call, @NonNull Response<List<Habit>> response) {
                if (response.body() != null && response.isSuccessful()){
                    habitsLiveData.postValue(response.body());
                }
                else{
                    habitsLiveData.postValue(new ArrayList<>());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Habit>> call, @NonNull Throwable t) {
                habitsLiveData.postValue(new ArrayList<>());
                Toast.makeText(getApplication().getApplicationContext(), R.string.no_internet, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void rechargeHabits(){
        loadHabits();
    }
}
