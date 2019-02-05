package com.mohan.fargoeventboard.services;

import com.mohan.fargoeventboard.data.Event;
import com.mohan.fargoeventboard.data.Speaker;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AppWebService {

    @POST("/login?Username={username}&Password={password}")
    Call<String> login(@Path("username") String username, @Path("password") String password);

    @GET("/events")
    Call<List<Event>> listEvents();

    @GET("events?id={id}")
    Call<Event> getEvent(@Path("id") String id);

    @GET("speakers?id={id}")
    Call<List<Speaker>> getSpeakers(@Path("id") String id);
}
