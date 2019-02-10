package com.mohan.fargoeventboard.data;

import com.google.gson.annotations.Expose;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Room database entity that represents an Event.
 */
@Entity
public class Event {

    @PrimaryKey
    @NonNull
    @Expose
    private int id;

    @Expose
    private String title;

    @Expose
    private String image_url;

    @Expose
    private String event_description;

    @Expose
    private Date start_date_time;

    @Expose
    private Date end_date_time;

    @Expose
    private String location;

    private Date lastRefresh;

    /**
     * A list of speaker ids that is used only to establish the connection between speakers and events.
     * Is not stored. The relationship is established with a foreign key in the database.
     */
    @Ignore
    private List<SpeakerId> speakers;

    public Event(@NonNull int id, Date lastRefresh){
        this.id = id;
        this.lastRefresh = lastRefresh;
    }

    /**
     * Returns a prettified date string.
     * @return The prettified date span for the event.
     */
    public String getPrettyDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy h:mma");

        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        startDate.setTime(start_date_time);
        endDate.setTime(end_date_time);
        boolean sameDay =  startDate.get(Calendar.YEAR) == endDate.get(Calendar.YEAR) && startDate.get(Calendar.DAY_OF_YEAR) == endDate.get(Calendar.DAY_OF_YEAR);
        if(sameDay){
            SimpleDateFormat timeFormat = new SimpleDateFormat("h:mma");
            return dateFormat.format(start_date_time) + " - " + timeFormat.format(end_date_time);
        } else {
            return dateFormat.format(start_date_time) + " - " + dateFormat.format(end_date_time);
        }
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public Date getLastRefresh() {
        return lastRefresh;
    }

    public void setLastRefresh(Date lastRefresh) {
        lastRefresh = lastRefresh;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getEnd_date_time() {
        return end_date_time;
    }

    public void setEnd_date_time(Date end_date_time) {
        this.end_date_time = end_date_time;
    }

    public Date getStart_date_time() {
        return start_date_time;
    }

    public void setStart_date_time(Date start_date_time) {
        this.start_date_time = start_date_time;
    }

    public String getEvent_description() {
        return event_description;
    }

    public void setEvent_description(String event_description) {
        this.event_description = event_description;
    }

    public List<SpeakerId> getSpeakers() {
        return speakers;
    }

    public void setSpeakers(List<SpeakerId> speakers) {
        this.speakers = speakers;
    }
}
