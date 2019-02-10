package com.mohan.fargoeventboard.data;

import android.content.SharedPreferences;

import com.mohan.fargoeventboard.services.AppWebService;

import java.io.Console;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import retrofit2.Call;
import retrofit2.Response;

import static com.mohan.fargoeventboard.MainActivity.LAST_LOAD_EVENTS;
import static com.mohan.fargoeventboard.MainActivity.LOGIN_TOKEN_PREF;

/**
 * Repository that handles most of the data for the application.
 */
@Singleton
public class AppRepository {

    //Minutes in the past at which the refresh functions should call the web server again.
    private static int REFRESH_LIMIT = 3;
    private final EventDao eventDao;
    private final SpeakerDao speakerDao;
    private final AppWebService webService;
    private final Executor executor;
    private SimpleDateFormat simpleDateFormat;
    public SharedPreferences sharedPreferences;

    /**
     * Creates an AppRepository
     * @param webService
     * @param eventDao
     * @param speakerDao
     * @param executor
     * @param sharedPreferences
     */
    @Inject
    public AppRepository(AppWebService webService, EventDao eventDao, SpeakerDao speakerDao, Executor executor, SharedPreferences sharedPreferences){
        this.webService = webService;
        this.eventDao = eventDao;
        this.executor = executor;
        this.speakerDao = speakerDao;
        this.sharedPreferences = sharedPreferences;
        simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa");
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
     * Gets a list of speakers associated with this event.
     * @param eventId
     * @return A LiveData list of speakers associated with this event.
     */
    public LiveData<List<Speaker>> getSpeakers(int eventId){
        return speakerDao.getSpeakersForEvent(eventId);
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
                            for (SpeakerId id: event.getSpeakers()) {
                                refreshSpeaker(id.getId(), event.getId());
                            }
                            event.setLastRefresh(new Date());
                            eventDao.insertEvent(event);
                        });
                    }

                    @Override
                    public void onFailure(Call<Event> call, Throwable t) { }
                });
            }
        });
    }


    /**
     * Currently fetches events if they haven't been updated in the last 3 minutes.
     */
    private void refreshAllEvents(){
        executor.execute(() -> {
            if(needToUpdateEvents()){
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
                sharedPreferences.edit().putString(LAST_LOAD_EVENTS, simpleDateFormat.format(new Date())).commit();
            }
        });
    }

    /**
     * Checks if the given speaker id exists, and if it has received the updated value from the the Server within the last 3 minutes.
     * If not, it fetches the corresponding speaker.
     * @param speakerId
     */
    private void refreshSpeaker(final int speakerId, final int eventId){
        executor.execute(() -> {
            boolean speakerExists = (speakerDao.hasSpeaker(speakerId, calculateRefreshTime(new Date())) != null);

            if(!speakerExists){
                webService.getSpeaker(speakerId, getStoredToken()).enqueue(new retrofit2.Callback<Speaker>(){

                    @Override
                    public void onResponse(Call<Speaker> call, Response<Speaker> response) {
                        executor.execute(() -> {
                            if (response.body() != null) {
                                Speaker speaker = response.body();
                                speaker.setLastRefresh(new Date());
                                speaker.setEventId(eventId);
                                speakerDao.insertSpeaker(speaker);
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<Speaker> call, Throwable t) {

                    }
                });
            }
        });
    }

    /**
     * A callback that allows the LoginFragment to be notified when the server has responded to its login request and act accordingly.
     */
    public interface LoginCallback {
        public enum ErrorCode{
            INVALID_CREDENTIALS, LOGIN_FAILED
        }
        void onResponse(Boolean success, @Nullable ErrorCode errorCode);
    }

    /**
     * Attempts to login the user.
     * @param username
     * @param password
     * @param loginCallback
     */
    public void login(String username, String password, final LoginCallback loginCallback){
        executor.execute(() -> {
            if(verifyCredentials(username, password)){
                webService.login(username, password).enqueue(new retrofit2.Callback<ResponseToken>() {
                    @Override
                    public void onResponse(Call<ResponseToken> call, Response<ResponseToken> response) {
                        SharedPreferences.Editor sharedPrefsEdit = sharedPreferences.edit();
                        sharedPrefsEdit.putString(LOGIN_TOKEN_PREF, response.body().getToken());
                        sharedPrefsEdit.commit();
                        loginCallback.onResponse(true, null);
                    }

                    @Override
                    public void onFailure(Call<ResponseToken> call, Throwable t) {
                        loginCallback.onResponse(false, LoginCallback.ErrorCode.LOGIN_FAILED);
                    }
                });
            } else {
                loginCallback.onResponse(false, LoginCallback.ErrorCode.INVALID_CREDENTIALS);
            }

        });
    }

    /**
     * Verifies that the provided credentials are valid for submission to the WebAPI.
     * @param username
     * @param password
     * @return
     */
    public boolean verifyCredentials(String username, String password){
        String regex = "^[a-zA-Z0-9]{4,10}$";
        return username.matches(regex) && password.matches(regex);
    }

    /**
     * Gets whether or not the Application has a login token.
     * @return True if logged in
     */
    public boolean getLoginStatus(){
        return !sharedPreferences.getString(LOGIN_TOKEN_PREF, "").isEmpty();
    }

    /**
     * Removes login credentials from the application.
     */
    public void logout(){
        sharedPreferences.edit().remove(LOGIN_TOKEN_PREF).commit();
    }

    /**
     * Determines if the list of events needs to be updated, based on the timestamp of the last time they were updated.
     * @return True if the events need to be updated
     */
    public boolean needToUpdateEvents(){
        String dateString = sharedPreferences.getString(LAST_LOAD_EVENTS, "");
        if(dateString.isEmpty()){
            return true;
        }
        try {
            Date date = simpleDateFormat.parse(dateString);
            return date.before(calculateRefreshTime(new Date()));
        } catch (ParseException e) {
            return true;
        }
    }

    /**
     * Returns the authorization token for the WebAPI.
     * @return
     */
    private String getStoredToken(){
        String temp = sharedPreferences.getString(LOGIN_TOKEN_PREF, "");
        return temp;
    }
}
