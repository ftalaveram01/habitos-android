package com.ftalaveram.habbbits.repositories.api;

import com.ftalaveram.habbbits.repositories.models.Habit;
import com.ftalaveram.habbbits.repositories.models.LoginData;
import com.ftalaveram.habbbits.repositories.models.LoginRequest;
import com.ftalaveram.habbbits.repositories.models.RegisterRequest;
import com.ftalaveram.habbbits.repositories.models.RegisterResponse;
import com.ftalaveram.habbbits.repositories.models.UserHabit;
import com.ftalaveram.habbbits.repositories.models.VerifyAccess;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class RemoteDataSource {

    private final ApiService apiService;

    public RemoteDataSource(ApiService apiService) {
        this.apiService = apiService;
    }


    //-------------------------------------------------------------------------------------------------
    //-----------------------------------USER METHODS--------------------------------------------------
    //-------------------------------------------------------------------------------------------------
    public void login(LoginRequest request, final Callback<LoginData> callback) {
        Call<LoginData> call = apiService.login(request);
        call.enqueue(callback);
    }

    public void register(RegisterRequest request, final Callback<RegisterResponse> callback){
        Call<RegisterResponse> call = apiService.register(request);
        call.enqueue(callback);
    }

    public void verifyAccess(String token, final Callback<VerifyAccess> callback){
        Call<VerifyAccess> call = apiService.verifyAccess(token);
        call.enqueue(callback);
    }

    //-------------------------------------------------------------------------------------------------
    //-----------------------------------HABIT METHODS-------------------------------------------------
    //-------------------------------------------------------------------------------------------------

    public void getUserHabits(String token, final Callback<List<UserHabit>> callback){
        Call<List<UserHabit>> call = apiService.getUserHabits(token);
        call.enqueue(callback);
    }

    public void getRecommendedHabits(final Callback<List<Habit>> callback){
        Call<List<Habit>> call = apiService.getHabitosRecomendados();
        call.enqueue(callback);
    }

}
