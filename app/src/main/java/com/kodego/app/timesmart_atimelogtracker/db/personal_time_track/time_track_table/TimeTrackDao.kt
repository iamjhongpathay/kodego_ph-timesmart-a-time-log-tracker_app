package com.kodego.app.timesmart_atimelogtracker.db.personal_time_track.time_track_table

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kodego.app.timesmart_atimelogtracker.db.personal_time_track.activity_type_table.ActivityType
import java.util.*

@Dao
interface TimeTrackDao {

    @Insert
    fun addTimeTrack(timeTrack: TimeTrack)

    @Query("SELECT * FROM time_track")
    fun getTimeTrack(): LiveData<MutableList<TimeTrack>>

    @Query("SELECT * FROM time_track WHERE dateCreated = :selectedDate ORDER BY dateTimeStarted DESC")
    fun getTimeTrackByDate(selectedDate: String): LiveData<MutableList<TimeTrack>>

    @Query("UPDATE time_track SET comment= :comment WHERE id=:id")
    fun updateTimeTracked(comment: String, id: Int)

    @Query("SELECT SUM(timeTracked) FROM time_track WHERE dateCreated = :selectedDate")
    fun getTotalTimeTracked(selectedDate: String) :Long

    @Delete
    fun deleteTimeTracked(timeTrack: TimeTrack)

    @Query("SELECT * FROM time_track WHERE dateCreated = :selectedDate")
    fun getAllTimeTrackedByDate(selectedDate: String) : MutableList<TimeTrack>

}