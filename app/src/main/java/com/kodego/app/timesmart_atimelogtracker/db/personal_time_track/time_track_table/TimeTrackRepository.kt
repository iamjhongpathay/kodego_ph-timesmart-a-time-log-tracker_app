package com.kodego.app.timesmart_atimelogtracker.db.personal_time_track.time_track_table

import androidx.lifecycle.LiveData
import com.kodego.app.timesmart_atimelogtracker.db.personal_time_track.activity_type_table.ActivityType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class TimeTrackRepository(private val timeTrackDao: TimeTrackDao) {

    val readAllDataTimeTrack = timeTrackDao.getTimeTrack()

    fun addTimeTrack(timeTrack: TimeTrack){
            timeTrackDao.addTimeTrack(timeTrack)
    }

    fun readTimeTrackByDate(selectedDate: String): LiveData<MutableList<TimeTrack>>{
        return timeTrackDao.getTimeTrackByDate(selectedDate)
    }

    fun updateTimeTracked(comment: String, id: Int){
        timeTrackDao.updateTimeTracked(comment, id)
    }

    fun getTotalTimeTracked(selectedDate: String): Long {
        return timeTrackDao.getTotalTimeTracked(selectedDate)
    }

    fun deleteTimeTracked(timeTrack: TimeTrack){
        timeTrackDao.deleteTimeTracked(timeTrack)
    }

    fun getAllTimeTrackedByDate(selectedDate: String) : MutableList<TimeTrack>{
        return timeTrackDao.getAllTimeTrackedByDate(selectedDate)
    }
}