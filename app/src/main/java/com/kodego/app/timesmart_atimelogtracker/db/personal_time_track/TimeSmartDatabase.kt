package com.kodego.app.timesmart_atimelogtracker.db.personal_time_track

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kodego.app.timesmart_atimelogtracker.db.personal_time_track.activity_type_table.ActivityType
import com.kodego.app.timesmart_atimelogtracker.db.personal_time_track.activity_type_table.ActivityTypeDao
import com.kodego.app.timesmart_atimelogtracker.db.personal_time_track.time_track_table.TimeTrack
import com.kodego.app.timesmart_atimelogtracker.db.personal_time_track.time_track_table.TimeTrackDao
import com.kodego.app.timesmart_atimelogtracker.db.personal_time_track.type_converter.Converter

@Database(
    entities = [ActivityType::class, TimeTrack::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converter::class)

abstract class TimeSmartDatabase : RoomDatabase() {
    abstract fun activityTypeDao() : ActivityTypeDao
    abstract fun timeTrackDao() :TimeTrackDao

    companion object{
        @Volatile
        private var instance: TimeSmartDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also{
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            TimeSmartDatabase :: class.java,
            "time_smart_database"
        ).createFromAsset("database/time_smart_database.db").allowMainThreadQueries().build()
    }
}