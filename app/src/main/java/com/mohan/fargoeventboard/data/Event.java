package com.mohan.fargoeventboard.data;

import java.util.Date;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Event {

    @PrimaryKey
    @NonNull
    private String id;

    private Date lastRefresh;

    public Event(@NonNull String id, Date lastRefresh){
        this.id = id;
        this.lastRefresh = lastRefresh;
    }



    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public Date getLastRefresh() {
        return lastRefresh;
    }

    public void setLastRefresh(Date lastRefresh) {
        lastRefresh = lastRefresh;
    }


}
