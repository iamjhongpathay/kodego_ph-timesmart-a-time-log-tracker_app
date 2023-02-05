package com.kodego.app.timesmart_atimelogtracker.db.personal_time_track.type_converter
import androidx.room.TypeConverter
import java.util.*

class Converter {

    //Date
    //toDB
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }
    //fromDB
    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

}