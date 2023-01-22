package com.kodego.app.timesmart_atimelogtracker.db.personal_time_track.activity_type_table

class ActivityTypeRepository(private val activityTypeDao: ActivityTypeDao){

    val readAllDataActivityTypes = activityTypeDao.getActivityTypes()

    fun addActivityType(activity: ActivityType){
        activityTypeDao.addActivityType(activity)
    }

    fun deleteActivityType(activity: ActivityType){
        activityTypeDao.deleteActivityType(activity)
    }

    fun updateActivityType(activity: ActivityType){
        activityTypeDao.updateActivityType(activity)
    }
}