package com.mohan.fargoeventboard.data;

import java.util.Date;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface EventDao {

    @Insert(onConflict = REPLACE)
    void insertEvent(Event event);

    @Insert
    void insertEvents(List<Event> eventList);

    @Query("SELECT * FROM event WHERE id = :eventId")
    LiveData<Event> load(String eventId);

    @Query("SELECT * FROM event")
    LiveData<List<Event>> loadAll();

    @Query("SELECT * FROM event WHERE id = :eventId AND lastRefresh > :lastRefreshMax LIMIT 1")
    Event hasEvent(String eventId, Date lastRefreshMax);


    
    
}
