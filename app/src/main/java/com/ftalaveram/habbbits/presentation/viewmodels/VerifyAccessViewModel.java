package com.ftalaveram.habbbits.presentation.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
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

    private final SessionManager sessionManager;

    public MutableLiveData<VerifyAccess> verifyAccess = new MutableLiveData<>();

    public VerifyAccessViewModel(@NonNull Application application) {
        super(application);
        sessionManager = new SessionManager(application.getApplicationContext());
    }

    public void verifyAccess() {
        repository.verifyAccess("Bearer " + sessionManager.getToken(), new Callback<VerifyAccess>() {
            @Override
            public void onResponse(@NonNull Call<VerifyAccess> call, @NonNull Response<VerifyAccess> response) {
                if(response.isSuccessful() && response.body() != null){
                    verifyAccess.postValue(new VerifyAccess(response.body().isAuthenticated()));
                }else{
                    verifyAccess.postValue(new VerifyAccess(false));
                }
            }

            @Override
            public void onFailure(@NonNull Call<VerifyAccess> call, @NonNull Throwable t) {
                verifyAccess.postValue(new VerifyAccess(false));
            }
        });
    }

}
