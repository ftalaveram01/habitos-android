package com.ftalaveram.habbbits.presentation.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.ftalaveram.habbbits.repositories.api.ApiClient;
import com.ftalaveram.habbbits.repositories.api.ApiService;
import com.ftalaveram.habbbits.repositories.api.RemoteDataSource;
import com.ftalaveram.habbbits.repositories.repository.HabitRepository;
import com.ftalaveram.habbbits.session.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteDialogViewModel extends AndroidViewModel {

    ApiService apiService = ApiClient.getApiService();
    RemoteDataSource remoteDataSource = new RemoteDataSource(apiService);
    HabitRepository repository = new HabitRepository(remoteDataSource);
    private SessionManager sessionManager;

    public final MutableLiveData<Void> deleteLiveData = new MutableLiveData<>();

    public DeleteDialogViewModel(@NonNull Application application){
        super(application);
        sessionManager = new SessionManager(application.getApplicationContext());
    }

    public void deleteHabit(Long id){
        repository.deleteHabit("Bearer " + sessionManager.getToken(), id, new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    deleteLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}
