package com.ftalaveram.habbbits.presentation.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.ftalaveram.habbbits.repositories.api.ApiClient;
import com.ftalaveram.habbbits.repositories.api.ApiService;
import com.ftalaveram.habbbits.repositories.api.RemoteDataSource;
import com.ftalaveram.habbbits.repositories.models.ProfileResponse;
import com.ftalaveram.habbbits.repositories.repository.UserRepository;
import com.ftalaveram.habbbits.session.SessionManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileViewModel extends AndroidViewModel {

    ApiService apiService = ApiClient.getApiService();
    RemoteDataSource remoteDataSource = new RemoteDataSource(apiService);
    UserRepository repository = new UserRepository(remoteDataSource);
    private SessionManager sessionManager;

    public final MutableLiveData<ProfileResponse> profileLiveData = new MutableLiveData<>();

    public ProfileViewModel(@NonNull Application application){
        super(application);
        sessionManager = new SessionManager(application.getApplicationContext());
        loadProfileData();
    }

    private void loadProfileData(){
        repository.getProfileData("Bearer " + sessionManager.getToken(), new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if (response.body() != null && response.isSuccessful()){
                    profileLiveData.postValue(response.body());
                }
                else{
                    profileLiveData.postValue(new ProfileResponse("", "", 0));
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                profileLiveData.postValue(new ProfileResponse("", "", 0));
            }
        });
    }

    public void rechargeProfileData(){
        loadProfileData();
    }
}
