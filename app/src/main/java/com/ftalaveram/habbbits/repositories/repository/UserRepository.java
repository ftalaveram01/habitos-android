package com.ftalaveram.habbbits.repositories.repository;

import com.ftalaveram.habbbits.repositories.api.RemoteDataSource;
import com.ftalaveram.habbbits.repositories.models.LoginData;
import com.ftalaveram.habbbits.repositories.models.LoginRequest;
import com.ftalaveram.habbbits.repositories.models.ProfileResponse;
import com.ftalaveram.habbbits.repositories.models.RegisterRequest;
import com.ftalaveram.habbbits.repositories.models.RegisterResponse;
import com.ftalaveram.habbbits.repositories.models.UpdatePasswordRequest;
import com.ftalaveram.habbbits.repositories.models.UpdateProfileRequest;
import com.ftalaveram.habbbits.repositories.models.UpdateResponse;
import com.ftalaveram.habbbits.repositories.models.VerifyAccess;

import retrofit2.Callback;

public class UserRepository {

    private final RemoteDataSource remoteDataSource;

    public UserRepository(RemoteDataSource remoteDataSource) {
        this.remoteDataSource = remoteDataSource;
    }

    public void login(LoginRequest request, Callback<LoginData> callback) {
        remoteDataSource.login(request, callback);
    }

    public void register(RegisterRequest request, Callback<RegisterResponse> callback){
        remoteDataSource.register(request, callback);
    }

    public void verifyAccess(String token, Callback<VerifyAccess> callback){
        remoteDataSource.verifyAccess(token, callback);
    }

    public void getProfileData(String token, Callback<ProfileResponse> callback){
        remoteDataSource.getProfileData(token, callback);
    }

    public void updateProfile(String token, UpdateProfileRequest request, Callback<UpdateResponse> callback){
        remoteDataSource.updateProfile(token, request, callback);
    }

    public void updatePassword(String token, UpdatePasswordRequest request, Callback<UpdateResponse> callback){
        remoteDataSource.updatePassword(token, request, callback);
    }
}
