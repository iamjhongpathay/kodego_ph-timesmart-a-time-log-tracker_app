package com.kodego.app.timesmart_atimelogtracker.db.personal_time_track.time_track_table

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
@Entity (tableName = "time_track")
data class TimeTrack(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val activityTypeName: String,
    val icon: String,
    val color: Int,
    val dateTimeStarted: Date,
    val dateCreated: String,
    val timeCreated: String,
    val timeStopped: String,
    val dateTimeStopped: Date,
    val timeTracked: Long,
    val comment: String?,

    ) : Parcelable