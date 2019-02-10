package com.mohan.fargoeventboard.dagger2;


import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mohan.fargoeventboard.data.AppDatabase;
import com.mohan.fargoeventboard.data.AppRepository;
import com.mohan.fargoeventboard.data.EventDao;
import com.mohan.fargoeventboard.data.SpeakerDao;
import com.mohan.fargoeventboard.services.AppWebService;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.mohan.fargoeventboard.MainActivity.SHARED_PREF_NAME;

/**
 * Module class that serves as a container for many of the providers for the application.
 */
@Module(includes = ViewModelModule.class)
public class AppModule {

    private static String BASE_URL = "https://challenge.myriadapps.com";


    /**
     * Provides the database for the application
     * @param application The application
     * @return The AppDatabase
     */
    @Provides
    @Singleton
    AppDatabase provideDatabase(Application application){
        return Room.databaseBuilder(application, AppDatabase.class, "AppDatabase.db").build();
    }

    /**
     * Provides the EventDao for the application
     * @param database
     * @return The EventDao
     */
    @Provides
    @Singleton
    EventDao provideEventDao(AppDatabase database){return database.eventDao();}

    /**
     * Provides the SpeakerDao for the application
     * @param database
     * @return The SpeakerDao
     */
    @Provides
    @Singleton
    SpeakerDao provideSpeakerDao(AppDatabase database){return database.speakerDao();}

    /**
     * Provides an executor instance, which allows easy running of background tasks.
     * @return An Executor instance
     */
    @Provides
    Executor provideExecutor() {
        return Executors.newSingleThreadExecutor();
    }

    /**
     * Provides an AppRepository, which handles the bulk of the data collection and management for the application.
     * @param webService
     * @param dao
     * @param speakerDao
     * @param executor
     * @param application
     * @return The AppRepository
     */
    @Provides
    @Singleton
    AppRepository provideAppRepository(AppWebService webService, EventDao dao, SpeakerDao speakerDao, Executor executor, Application application){
        return new AppRepository(webService, dao, speakerDao, executor, application.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE));
    }


    /**
     * Provides a Gson instance.
     * @return A Gson instance
     */
    @Provides
    Gson provideGson() { return new GsonBuilder().create(); }

    /**
     * Provides a Retrofit instance. Allows fetching data from the WebAPI.
     * @param gson
     * @return A Retrofit instance
     */
    @Provides
    Retrofit provideRetrofit(Gson gson){
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL)
                .build();
        return retrofit;
    }

    /**
     * Provides an instance of the AppWebService, which handles WebAPI calls.
     * @param restAdapter
     * @return AppWebService instance
     */
    @Provides
    AppWebService provideAppWebService(Retrofit restAdapter){
        return restAdapter.create(AppWebService.class);
    }

}
