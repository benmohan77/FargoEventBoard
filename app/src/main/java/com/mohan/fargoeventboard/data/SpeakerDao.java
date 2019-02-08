package com.mohan.fargoeventboard.data;

import java.util.Date;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface SpeakerDao {

    @Insert(onConflict = REPLACE)
    void insertSpeaker(Speaker speaker);

    @Insert(onConflict = REPLACE)
    void insertSpeakers(List<Speaker> speakerList);

    @Query("SELECT * FROM speaker WHERE id = :speakerId")
    LiveData<Speaker> load(int speakerId);

    @Query("SELECT * FROM speaker")
    LiveData<List<Speaker>> loadAll();

    @Query("SELECT * FROM speaker WHERE event_id=:eventId")
    LiveData<List<Speaker>> getSpeakersForEvent(int eventId);

    @Query("SELECT * FROM speaker WHERE id = :speakerId AND lastRefresh > :lastRefreshMax LIMIT 1")
    Speaker hasSpeaker(int speakerId, Date lastRefreshMax);
}
