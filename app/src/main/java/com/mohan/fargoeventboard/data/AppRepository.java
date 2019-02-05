package com.mohan.fargoeventboard.data;

import android.content.SharedPreferences;

import com.mohan.fargoeventboard.services.AppWebService;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.LiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class AppRepository {

    //Minutes in the past at which the refresh functions should call the web server again.
    private static int REFRESH_LIMIT = 3;

    private static final String LOGIN_TOKEN_PREF = "TOKEN_PREF";

    private final EventDao eventDao;
    private final AppWebService webService;
    private final Executor executor;

//    @Inject
//    public SharedPreferences sharedPreferences;

    @Inject
    public AppRepository(AppWebService webService, EventDao eventDao, Executor executor){
        this.webService = webService;
        this.eventDao = eventDao;
        this.executor = executor;;
    }

    /**
     * Gets the specified event
     * @param eventId
     * @return The corresponding event
     */
    public LiveData<Event> getEvent(String eventId){
        refreshEvent(eventId);
        return eventDao.load(eventId);
    }

    /**
     * Fetches a list of all the Events
     * @return A LiveData list of all the events.
     */
    public LiveData<List<Event>> getAllEvents(){
        refreshAllEvents();
        return eventDao.loadAll();
    }

    /**
     * Calculates the time limit at which the server should be called for a given database entry.
     * @param currentDate
     * @return The current time 3 minutes in the past.
     */
    private Date calculateRefreshTime(Date currentDate){
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        cal.add(Calendar.MINUTE, -REFRESH_LIMIT);
        return cal.getTime();
    }

    /**
     * Checks if the given event id exists, and if it has received the updated value from the the Server within the last 3 minutes.
     * If not, it fetches the corresponding event.
     * @param eventId
     */
    private void refreshEvent(final String eventId){
        executor.execute(() -> {
            boolean eventExists = (eventDao.hasEvent(eventId, calculateRefreshTime(new Date())) != null);

            if(!eventExists){
                webService.getEvent(eventId).enqueue(new Callback<Event>() {
                    @Override
                    public void onResponse(Call<Event> call, Response<Event> response) {
                        executor.execute(() -> {
                            Event event = response.body();
                            event.setLastRefresh(new Date());
                            eventDao.insertEvent(event);
                        });
                    }

                    @Override
                    public void onFailure(Call<Event> call, Throwable t) {}
                });
            }
        });
    }

    /**
     * Currently just fetches all Events from the server regardless of whether or not they already exist.
     */
    private void refreshAllEvents(){
        executor.execute(() -> {
            webService.listEvents().enqueue(new Callback<List<Event>>() {
                @Override
                public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                    executor.execute(() -> {
                        eventDao.insertEvents(response.body());
                    });
                }

                @Override
                public void onFailure(Call<List<Event>> call, Throwable t) {

                }
            });
        });
    }

    public void login(String username, String password){
        executor.execute(() -> {
            webService.login(username, password).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
          //          sharedPreferences.edit().putString(LOGIN_TOKEN_PREF, response.body());
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });
        });
    }

    public void logout(){
       // sharedPreferences.edit().remove(LOGIN_TOKEN_PREF);
    }
}
