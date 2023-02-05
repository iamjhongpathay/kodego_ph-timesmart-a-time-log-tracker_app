package com.kodego.app.timesmart_atimelogtracker.db.personal_time_track.time_track_table

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.kodego.app.timesmart_atimelogtracker.db.personal_time_track.TimeSmartDatabase
import com.kodego.app.timesmart_atimelogtracker.db.personal_time_track.activity_type_table.ActivityType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.w3c.dom.Comment
import java.util.Date

class TimeTrackViewModel(application: Application) : AndroidViewModel(application) {
    val readAllDataTimeTrack : LiveData<MutableList<TimeTrack>>
    private val timeTrackRepository : TimeTrackRepository

    init{
        val timeTrackDao = TimeSmartDatabase.invoke(application).timeTrackDao()
        timeTrackRepository = TimeTrackRepository(timeTrackDao)
        this.readAllDataTimeTrack = timeTrackRepository.readAllDataTimeTrack
    }

    fun addTimeTrack(timeTrack: TimeTrack){
        GlobalScope.launch(Dispatchers.IO) {
            timeTrackRepository.addTimeTrack(timeTrack)
        }
    }

    fun readTimeTrackByDate(selectedDate: String): LiveData<MutableList<TimeTrack>>{
        return timeTrackRepository.readTimeTrackByDate(selectedDate)
    }

    fun updateTimeTracked(comment: String, id: Int){
        GlobalScope.launch(Dispatchers.IO) {
            timeTrackRepository.updateTimeTracked(comment, id)
        }
    }

    fun getTotalTimeTracked(selectedDate: String): Long {
        return timeTrackRepository.getTotalTimeTracked(selectedDate)
    }

    fun deleteTimeTracked(timeTrack: TimeTrack){
        GlobalScope.launch ( Dispatchers.IO ){
            timeTrackRepository.deleteTimeTracked(timeTrack)
        }
    }

    fun getAllTimeTrackedByDate(selectedDate: String): MutableList<TimeTrack>{
        return timeTrackRepository.getAllTimeTrackedByDate(selectedDate)
    }
}