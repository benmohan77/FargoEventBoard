package com.mohan.fargoeventboard.services;

import com.mohan.fargoeventboard.data.Event;
import com.mohan.fargoeventboard.data.Speaker;
import com.mohan.fargoeventboard.data.ResponseToken;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AppWebService {

    @POST("/api/v1/login")
    Call<ResponseToken> login(@Query("Username") String username, @Query("Password") String password);

    @GET("/api/v1/events")
    Call<List<Event>> listEvents(@Header("Authorization") String token);

    @GET("/api/v1/events/{id}")
    Call<Event> getEvent(@Path("id") int id, @Header("Authorization") String token);

    @GET("/api/v1/speakers/{id}")
    Call<List<Speaker>> getSpeakers(@Path("id") String id, @Header("Authorization") String token);
}
