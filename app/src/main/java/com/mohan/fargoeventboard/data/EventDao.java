package com.mohan.fargoeventboard.data;

import java.util.Date;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import static androidx.room.OnConflictStrategy.REPLACE;

/**
 * Allows access to the database for Event entities.
 */
@Dao
public interface EventDao {

    @Insert(onConflict = REPLACE)
    void insertEvent(Event event);

    @Insert(onConflict = REPLACE)
    void insertEvents(List<Event> eventList);

    @Query("SELECT * FROM event WHERE id = :eventId")
    LiveData<Event> load(int eventId);

    @Query("SELECT * FROM event")
    LiveData<List<Event>> loadAll();

    @Query("SELECT * FROM event WHERE id = :eventId AND lastRefresh > :lastRefreshMax LIMIT 1")
    Event hasEvent(int eventId, Date lastRefreshMax);


    
    
}
