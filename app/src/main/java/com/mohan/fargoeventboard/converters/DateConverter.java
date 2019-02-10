package com.mohan.fargoeventboard.converters;

import java.util.Date;

import androidx.room.TypeConverter;

/**
 * Used to handle the conversion of Dates to Strings and back for Room database.
 */
public class DateConverter {

    @TypeConverter
    public static Date toDate(Long timestamp){
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date){
        return date == null ? null : date.getTime();
    }
}
