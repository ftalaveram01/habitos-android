package com.ftalaveram.habbbits.repositories.repository;

import com.ftalaveram.habbbits.repositories.api.RemoteDataSource;
import com.ftalaveram.habbbits.repositories.models.CreateRequest;
import com.ftalaveram.habbbits.repositories.models.CreateResponse;
import com.ftalaveram.habbbits.repositories.models.Habit;
import com.ftalaveram.habbbits.repositories.models.UpdateRequest;
import com.ftalaveram.habbbits.repositories.models.UpdateResponse;
import com.ftalaveram.habbbits.repositories.models.UserHabit;

import java.util.List;

import retrofit2.Callback;

public class HabitRepository {

    private final RemoteDataSource remoteDataSource;

    public HabitRepository(RemoteDataSource remoteDataSource) {
        this.remoteDataSource = remoteDataSource;
    }

    public void getUserHabits(String token, Callback<List<UserHabit>> callback){
        remoteDataSource.getUserHabits(token, callback);
    }

    public void getRecommendedHabits(Callback<List<Habit>> callback){
        remoteDataSource.getRecommendedHabits(callback);
    }

    public void createHabit(String token, CreateRequest request, Callback<CreateResponse> callback){
        remoteDataSource.createHabit(token, request, callback);
    }

    public void updateHabit(String token, UpdateRequest request, Long id, Callback<UpdateResponse> callback){
        remoteDataSource.updateHabit(token, request, id, callback);
    }
}
