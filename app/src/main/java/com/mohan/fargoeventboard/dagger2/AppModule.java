package com.mohan.fargoeventboard.dagger2;


import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mohan.fargoeventboard.data.AppDatabase;
import com.mohan.fargoeventboard.data.AppRepository;
import com.mohan.fargoeventboard.data.EventDao;
import com.mohan.fargoeventboard.services.AppWebService;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = ViewModelModule.class)
public class AppModule {

    @Provides
    @Singleton
    AppDatabase provideDatabase(Application application){
        return Room.databaseBuilder(application, AppDatabase.class, "AppDatabase.db").build();
    }

    @Provides
    @Singleton
    EventDao provideEventDao(AppDatabase database){return database.eventDao();}

    @Provides
    Executor provideExecutor() {
        return Executors.newSingleThreadExecutor();
    }

    @Provides
    @Singleton
    AppRepository provideAppRepository(AppWebService webService, EventDao dao, Executor executor){
        return new AppRepository(webService, dao, executor);
    }

    private static String BASE_URL = "https://challenge.myriadapps.com/api/v1/";

    @Provides
    Gson provideGson() { return new GsonBuilder().create(); }

    @Provides
    Retrofit provideRetrofit(Gson gson){
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL)
                .build();
        return retrofit;
    }

    private static String SHARED_PREF_NAME = "sharedPrefs";

//    @Provides
//    @Singleton
//    SharedPreferences provideSharedPreference(Application application){
//        return application.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
//    }

    @Provides
    AppWebService provideAppWebService(Retrofit restAdapter){
        return restAdapter.create(AppWebService.class);
    }

}
