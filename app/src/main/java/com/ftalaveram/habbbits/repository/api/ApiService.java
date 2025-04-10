package com.ftalaveram.habbbits.repository.api;

import retrofit2.Call;
import retrofit2.http.GET;

import com.ftalaveram.habbbits.repository.models.Habit;

import java.util.List;

public interface ApiService {

    @GET("/habitos/recomendados")
    Call<List<Habit>> getHabitosRecomendados();
}
