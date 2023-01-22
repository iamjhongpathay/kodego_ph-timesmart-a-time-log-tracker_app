package com.kodego.app.timesmart_atimelogtracker.db.personal_time_track.activity_type_table

import android.app.Application
import android.provider.Settings.Global
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.kodego.app.timesmart_atimelogtracker.db.personal_time_track.TimeSmartDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ActivityTypeViewModel(application: Application) : AndroidViewModel(application){
    val readAllDataActivityTypes : LiveData<MutableList<ActivityType>>
    private val activityTypeRepository : ActivityTypeRepository

    init {
        val activityTypeDao = TimeSmartDatabase.invoke(application).activityTypeDao()
        activityTypeRepository = ActivityTypeRepository(activityTypeDao)
        readAllDataActivityTypes = activityTypeRepository.readAllDataActivityTypes
    }

    fun addActivityType(activityType: ActivityType){
        GlobalScope.launch(Dispatchers.IO) {
            activityTypeRepository.addActivityType(activityType)
        }
    }

    fun deleteActivityType(activityType: ActivityType){
        GlobalScope.launch ( Dispatchers.IO ){
            activityTypeRepository.deleteActivityType(activityType)
        }
    }

    fun updateActivityType(activityType: ActivityType){
        GlobalScope.launch(Dispatchers.IO) {
            activityTypeRepository.updateActivityType(activityType)
        }
    }
}