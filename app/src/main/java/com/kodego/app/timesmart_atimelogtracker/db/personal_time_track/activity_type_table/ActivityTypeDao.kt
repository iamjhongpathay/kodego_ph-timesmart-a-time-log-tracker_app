package com.kodego.app.timesmart_atimelogtracker.db.personal_time_track.activity_type_table

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ActivityTypeDao {
    @Insert
    fun addActivityType(activityType: ActivityType)

    @Query("SELECT * FROM activity_types")
    fun getActivityTypes(): LiveData<MutableList<ActivityType>>

    @Delete
    fun deleteActivityType(activityType: ActivityType)

   @Update
   fun updateActivityType(activityType: ActivityType)

}