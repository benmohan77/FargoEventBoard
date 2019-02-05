package com.mohan.fargoeventboard.data;

import com.google.gson.annotations.Expose;

import java.net.URL;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

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
    private Date start_date_time;

    @Expose
    private Date end_date_time;

    @Expose
    private String location;

    private Date lastRefresh;

    public Event(@NonNull int id, Date lastRefresh){
        this.id = id;
        this.lastRefresh = lastRefresh;
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
}
