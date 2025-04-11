package com.ftalaveram.habbbits.repositories.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

import com.ftalaveram.habbbits.repositories.models.Habit;
import com.ftalaveram.habbbits.repositories.models.LoginData;
import com.ftalaveram.habbbits.repositories.models.LoginRequest;
import com.ftalaveram.habbbits.repositories.models.VerifyAccess;

import java.util.List;

public interface ApiService {

    @GET("/habitos/recomendados")
    Call<List<Habit>> getHabitosRecomendados();

    @POST("/usuarios/login")
    Call<LoginData> login(@Body LoginRequest request);

    @GET("/usuarios/verifyaccess")
    Call<VerifyAccess> verifyAccess(@Header("Authorization") String token);
}
