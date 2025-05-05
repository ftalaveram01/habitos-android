package com.ftalaveram.habbbits.presentation.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.ftalaveram.habbbits.repositories.api.ApiClient;
import com.ftalaveram.habbbits.repositories.api.ApiService;
import com.ftalaveram.habbbits.repositories.api.RemoteDataSource;
import com.ftalaveram.habbbits.repositories.models.LoginData;
import com.ftalaveram.habbbits.repositories.models.LoginRequest;
import com.ftalaveram.habbbits.repositories.repository.UserRepository;
import com.ftalaveram.habbbits.session.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {

    ApiService apiService = ApiClient.getApiService();
    RemoteDataSource remoteDataSource = new RemoteDataSource(apiService);
    UserRepository repository = new UserRepository(remoteDataSource);

    private final SessionManager sessionManager;

    public MutableLiveData<LoginData> loginData = new MutableLiveData<>();

    public LoginViewModel(@NonNull Application application) {
        super(application);
        sessionManager = new SessionManager(application.getApplicationContext());
    }

    public void login(String email, String password) {
        repository.login(new LoginRequest(email, password), new Callback<LoginData>() {
            @Override
            public void onResponse(@NonNull Call<LoginData> call, @NonNull Response<LoginData> response) {
                if (response.body() != null){
                    sessionManager.saveToken(response.body().getToken());
                    loginData.postValue(new LoginData(response.body().isSuccess(), null, response.body().getToken()));
                }else{
                    loginData.postValue(new LoginData(false, null, null));
                }

            }

            @Override
            public void onFailure(@NonNull Call<LoginData> call, @NonNull Throwable t) {
                loginData.postValue(new LoginData(false, null, null));
            }
        });
    }

}
