package com.ftalaveram.habbbits.repositories.repository;

import com.ftalaveram.habbbits.repositories.api.RemoteDataSource;
import com.ftalaveram.habbbits.repositories.models.Achievements;

import java.util.List;

import retrofit2.Callback;

public class AchievementsRepository {

    private final RemoteDataSource remoteDataSource;

    public AchievementsRepository(RemoteDataSource remoteDataSource){
        this.remoteDataSource = remoteDataSource;
    }

    public void getAchievements(String token, Callback<List<Achievements>> callback){
        remoteDataSource.getAchievements(token, callback);
    }
}
