package com.ftalaveram.habbbits.repositories.api;

import com.ftalaveram.habbbits.repositories.models.Achievements;
import com.ftalaveram.habbbits.repositories.models.CreateRequest;
import com.ftalaveram.habbbits.repositories.models.CreateResponse;
import com.ftalaveram.habbbits.repositories.models.DoneResponse;
import com.ftalaveram.habbbits.repositories.models.Habit;
import com.ftalaveram.habbbits.repositories.models.LoginData;
import com.ftalaveram.habbbits.repositories.models.LoginRequest;
import com.ftalaveram.habbbits.repositories.models.ProfileResponse;
import com.ftalaveram.habbbits.repositories.models.RegisterRequest;
import com.ftalaveram.habbbits.repositories.models.RegisterResponse;
import com.ftalaveram.habbbits.repositories.models.UpdateRequest;
import com.ftalaveram.habbbits.repositories.models.UpdateResponse;
import com.ftalaveram.habbbits.repositories.models.UserHabit;
import com.ftalaveram.habbbits.repositories.models.VerifyAccess;

import java.net.CacheRequest;
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

    public void getProfileData(String token, final Callback<ProfileResponse> callback){
        Call<ProfileResponse> call = apiService.getProfileData(token);
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

    public void createHabit(String token, CreateRequest request, final Callback<CreateResponse> callback){
        Call<CreateResponse> call = apiService.createHabit(token, request);
        call.enqueue(callback);
    }

    public void updateHabit(String token, UpdateRequest request, Long id, final Callback<UpdateResponse> callback){
        Call<UpdateResponse> call = apiService.updateHabit(token, request, id);
        call.enqueue(callback);
    }

    //-------------------------------------------------------------------------------------------------
    //-----------------------------------ACHIEVEMENTS METHODS-------------------------------------------------
    //-------------------------------------------------------------------------------------------------

    public void getAchievements(String token, final Callback<List<Achievements>> callback){
        Call<List<Achievements>> call = apiService.getAchievements(token);
        call.enqueue(callback);
    }

    public void habitDone(String token, Long id, final Callback<DoneResponse> callback){
        Call<DoneResponse> call = apiService.habitDone(token, id);
        call.enqueue(callback);
    }

}
