package com.mohan.fargoeventboard.data;

import com.mohan.fargoeventboard.converters.DateConverter;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Event.class, Speaker.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    //Singleton
    private static volatile AppDatabase INSTANCE;

    //DAO
    public abstract EventDao eventDao();

}
