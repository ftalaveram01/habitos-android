package com.ftalaveram.habbbits.presentation.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ftalaveram.habbbits.repositories.api.ApiClient;
import com.ftalaveram.habbbits.repositories.api.ApiService;
import com.ftalaveram.habbbits.repositories.api.RemoteDataSource;
import com.ftalaveram.habbbits.repositories.models.VerifyAccess;
import com.ftalaveram.habbbits.repositories.repository.UserRepository;
import com.ftalaveram.habbbits.session.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyAccessViewModel extends AndroidViewModel {

    ApiService apiService = ApiClient.getApiService();
    RemoteDataSource remoteDataSource = new RemoteDataSource(apiService);
    UserRepository repository = new UserRepository(remoteDataSource);

    private SessionManager sessionManager;

    public MutableLiveData<VerifyAccess> verifyAccess = new MutableLiveData<>();

    public VerifyAccessViewModel(@NonNull Application application) {
        super(application);
        sessionManager = new SessionManager(application.getApplicationContext());
    }

    public void verifyAccess() {
        repository.verifyAccess("Bearer " + sessionManager.getToken(), new Callback<VerifyAccess>() {
            @Override
            public void onResponse(Call<VerifyAccess> call, Response<VerifyAccess> response) {
                if(response.isSuccessful() && response.body() != null){
                    verifyAccess.postValue(new VerifyAccess(response.body().isAuthenticated()));
                }else{
                    verifyAccess.postValue(new VerifyAccess(false));
                }
                Log.d("TOKEN", sessionManager.getToken());
            }

            @Override
            public void onFailure(Call<VerifyAccess> call, Throwable t) {
                verifyAccess.postValue(new VerifyAccess(false));
            }
        });
    }

    public LiveData<VerifyAccess> getVerifyAccessResult() {
        return verifyAccess;
    }
}
