package com.mohan.fargoeventboard.data;

import android.content.SharedPreferences;

import com.mohan.fargoeventboard.services.AppWebService;

import java.io.Console;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.LiveData;
import retrofit2.Call;
import retrofit2.Response;

import static com.mohan.fargoeventboard.MainActivity.LOGIN_TOKEN_PREF;

@Singleton
public class AppRepository {

    //Minutes in the past at which the refresh functions should call the web server again.
    private static int REFRESH_LIMIT = 3;


    private final EventDao eventDao;
    private final AppWebService webService;
    private final Executor executor;

    public SharedPreferences sharedPreferences;

    @Inject
    public AppRepository(AppWebService webService, EventDao eventDao, Executor executor, SharedPreferences sharedPreferences){
        this.webService = webService;
        this.eventDao = eventDao;
        this.executor = executor;
        this.sharedPreferences = sharedPreferences;
    }

    /**
     * Gets the specified event
     * @param eventId
     * @return The corresponding event
     */
    public LiveData<Event> getEvent(int eventId){
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
    private void refreshEvent(final int eventId){
        executor.execute(() -> {
            boolean eventExists = (eventDao.hasEvent(eventId, calculateRefreshTime(new Date())) != null);

            if(!eventExists){
                webService.getEvent(eventId, getStoredToken()).enqueue(new retrofit2.Callback<Event>() {
                    @Override
                    public void onResponse(Call<Event> call, Response<Event> response) {
                        executor.execute(() -> {
                            Event event = response.body();
                            event.setLastRefresh(new Date());
                            eventDao.insertEvent(event);
                        });
                    }

                    @Override
                    public void onFailure(Call<Event> call, Throwable t) {
                        System.out.println(t.toString());
                    }
                });
            }
        });
    }

    /**
     * Currently just fetches all Events from the server regardless of whether or not they already exist.
     */
    private void refreshAllEvents(){
        executor.execute(() -> {
            webService.listEvents(getStoredToken()).enqueue(new retrofit2.Callback<List<Event>>() {
                @Override
                public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                    executor.execute(() -> {
                        if(response.body() != null){
                            eventDao.insertEvents(response.body());
                        }
                    });
                }

                @Override
                public void onFailure(Call<List<Event>> call, Throwable t) {

                }
            });
        });
    }

    public interface LoginCallback {
        void onResponse(Boolean success);
    }

    public void login(String username, String password, final LoginCallback loginCallback){
        executor.execute(() -> {
            webService.login(username, password).enqueue(new retrofit2.Callback<ResponseToken>() {
                @Override
                public void onResponse(Call<ResponseToken> call, Response<ResponseToken> response) {
                    SharedPreferences.Editor sharedPrefsEdit = sharedPreferences.edit();
                    sharedPrefsEdit.putString(LOGIN_TOKEN_PREF, response.body().getToken());
                    sharedPrefsEdit.commit();
                    loginCallback.onResponse(true);
                }

                @Override
                public void onFailure(Call<ResponseToken> call, Throwable t) {
                    loginCallback.onResponse(false);
                }
            });
        });
    }

    public boolean getLoginStatus(){
        return !sharedPreferences.getString(LOGIN_TOKEN_PREF, "").isEmpty();
    }

    public void logout(){
        sharedPreferences.edit().remove(LOGIN_TOKEN_PREF);
    }

    private String getStoredToken(){
        String temp = sharedPreferences.getString(LOGIN_TOKEN_PREF, "");
        return temp;
    }
}
