package com.ftalaveram.habbbits.presentation.viewmodels;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.ftalaveram.habbbits.R;
import com.ftalaveram.habbbits.repositories.api.ApiClient;
import com.ftalaveram.habbbits.repositories.api.ApiService;
import com.ftalaveram.habbbits.repositories.api.RemoteDataSource;
import com.ftalaveram.habbbits.repositories.models.ProfileResponse;
import com.ftalaveram.habbbits.repositories.models.UpdatePasswordRequest;
import com.ftalaveram.habbbits.repositories.models.UpdateProfileRequest;
import com.ftalaveram.habbbits.repositories.models.UpdateResponse;
import com.ftalaveram.habbbits.repositories.repository.UserRepository;
import com.ftalaveram.habbbits.session.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileViewModel extends AndroidViewModel {

    ApiService apiService = ApiClient.getApiService();
    RemoteDataSource remoteDataSource = new RemoteDataSource(apiService);
    UserRepository repository = new UserRepository(remoteDataSource);
    private final SessionManager sessionManager;

    public final MutableLiveData<ProfileResponse> profileLiveData = new MutableLiveData<>();
    public final MutableLiveData<UpdateResponse> updateLiveData = new MutableLiveData<>();
    public final MutableLiveData<UpdateResponse> updatePasswordLiveData = new MutableLiveData<>();

    public ProfileViewModel(@NonNull Application application){
        super(application);
        sessionManager = new SessionManager(application.getApplicationContext());
    }

    public void logOut(){
        sessionManager.logout();
    }

    private void loadProfileData(){
        repository.getProfileData("Bearer " + sessionManager.getToken(), new Callback<ProfileResponse>() {
            @Override
            public void onResponse(@NonNull Call<ProfileResponse> call, @NonNull Response<ProfileResponse> response) {
                if (response.body() != null && response.isSuccessful()){
                    profileLiveData.postValue(response.body());
                }
                else{
                    profileLiveData.postValue(new ProfileResponse("", "", 0));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ProfileResponse> call, @NonNull Throwable t) {
                profileLiveData.postValue(new ProfileResponse("", "", 0));
                Toast.makeText(getApplication().getApplicationContext(), R.string.no_internet, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateProfile(String email, String name){
        repository.updateProfile("Bearer " + sessionManager.getToken(), new UpdateProfileRequest(email, name), new Callback<UpdateResponse>() {
            @Override
            public void onResponse(@NonNull Call<UpdateResponse> call, @NonNull Response<UpdateResponse> response) {
                if (response.isSuccessful() && response.body() != null){
                    updateLiveData.postValue(response.body());
                }else{
                    updateLiveData.postValue(new UpdateResponse(false));
                }
            }

            @Override
            public void onFailure(@NonNull Call<UpdateResponse> call, @NonNull Throwable t) {
                updateLiveData.postValue(new UpdateResponse(false));
                Toast.makeText(getApplication().getApplicationContext(), R.string.no_internet, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updatePassword(String newPassword, String password){
        repository.updatePassword("Bearer " + sessionManager.getToken(), new UpdatePasswordRequest(newPassword, password), new Callback<UpdateResponse>() {
            @Override
            public void onResponse(@NonNull Call<UpdateResponse> call, @NonNull Response<UpdateResponse> response) {
                if (response.isSuccessful() && response.body() != null){
                    updatePasswordLiveData.postValue(response.body());
                }else{
                    updatePasswordLiveData.postValue(new UpdateResponse(false));
                }
            }

            @Override
            public void onFailure(@NonNull Call<UpdateResponse> call, @NonNull Throwable t) {
                updatePasswordLiveData.postValue(new UpdateResponse(false));
                Toast.makeText(getApplication().getApplicationContext(), R.string.no_internet, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void rechargeProfileData(){
        loadProfileData();
    }
}
