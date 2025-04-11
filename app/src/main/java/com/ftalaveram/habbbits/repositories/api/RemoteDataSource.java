package com.ftalaveram.habbbits.repositories.api;

import com.ftalaveram.habbbits.repositories.models.LoginData;
import com.ftalaveram.habbbits.repositories.models.LoginRequest;
import com.ftalaveram.habbbits.repositories.models.VerifyAccess;

import retrofit2.Call;
import retrofit2.Callback;

public class RemoteDataSource {

    private final ApiService apiService;

    public RemoteDataSource(ApiService apiService) {
        this.apiService = apiService;
    }

    public void login(LoginRequest request, final Callback<LoginData> callback) {
        Call<LoginData> call = apiService.login(request);
        call.enqueue(callback);
    }

    public void verifyAccess(String token, final Callback<VerifyAccess> callback){
        Call<VerifyAccess> call = apiService.verifyAccess(token);
        call.enqueue(callback);
    }

}
