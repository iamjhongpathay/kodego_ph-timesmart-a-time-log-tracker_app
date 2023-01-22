package com.kodego.app.timesmart_atimelogtracker.db.personal_time_track.activity_type_table

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "activity_types")
data class ActivityType(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String,
    var icon: String,
    var color: Int,
    ) : Parcelable