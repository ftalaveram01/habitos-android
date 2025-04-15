package com.ftalaveram.habbbits.repositories.repository;

import com.ftalaveram.habbbits.repositories.api.RemoteDataSource;
import com.ftalaveram.habbbits.repositories.models.Habit;
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
}
