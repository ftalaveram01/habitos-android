package com.ftalaveram.habbbits.repositories.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

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

import java.util.List;

public interface ApiService {

    @POST("/usuarios/login")
    Call<LoginData> login(@Body LoginRequest request);

    @POST("/usuarios/register")
    Call<RegisterResponse> register(@Body RegisterRequest request);

    @GET("/usuarios/verifyaccess")
    Call<VerifyAccess> verifyAccess(@Header("Authorization") String token);

    @GET("usuarios/datauser")
    Call<ProfileResponse> getProfileData(@Header("Authorization") String token);

    @GET("habitos/usuario")
    Call<List<UserHabit>> getUserHabits(@Header("Authorization") String token);

    @GET("/habitos/recomendados")
    Call<List<Habit>> getHabitosRecomendados();

    @POST("/habitos")
    Call<CreateResponse> createHabit(@Header("Authorization") String token, @Body CreateRequest request);

    @PUT("/habitos/{id}")
    Call<UpdateResponse> updateHabit(@Header("Authorization") String token, @Body UpdateRequest request, @Path("id") Long id);

    @GET("/historial")
    Call<List<Achievements>> getAchievements(@Header("Authorization") String token);

    @POST("/historial/{id}")
    Call<DoneResponse> habitDone(@Header("Authorization") String token, @Path("id") Long id);


}
