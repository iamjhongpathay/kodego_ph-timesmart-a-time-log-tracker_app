package com.kodego.app.timesmart_atimelogtracker.ui.personal_time_track.home

import android.content.Context
import android.content.SharedPreferences
import java.text.SimpleDateFormat
import java.util.*

class DataHelper(context: Context) {

    private var sharedPref : SharedPreferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
    private var dateFormat = SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.getDefault())

    private var startTime: Date? = null
    private var pauseTime: Date? = null
    private var timerCounting = false

    init{
        timerCounting  = sharedPref.getBoolean(COUNTING_KEY, false)

        val startString = sharedPref.getString(START_TIME_KEY, null)
        if(startString != null){
            startTime = dateFormat.parse(startString)
        }

        val pauseString = sharedPref.getString(PAUSE_TIME_KEY, null)
        if(pauseString != null){
            pauseTime = dateFormat.parse(pauseString)
        }

    }

    fun startTime(): Date? = startTime
    fun setStartTime(date: Date?){
        startTime = date
        with(sharedPref.edit()){
            val stringDate = if (date == null) null else dateFormat.format(date)
            putString(START_TIME_KEY, stringDate)
            apply()
        }
    }

    fun pauseTime(): Date? = pauseTime
    fun setPauseTime(date: Date?){
        pauseTime = date
        with(sharedPref.edit()){
            val stringDate = if (date == null) null else dateFormat.format(date)
            putString(PAUSE_TIME_KEY, stringDate)
            apply()
        }
    }

    fun timerCounting(): Boolean = timerCounting
    fun setTimerCounting(value : Boolean){
        timerCounting = value
        with(sharedPref.edit()){
            putBoolean(COUNTING_KEY, value)
            apply()
        }
    }

    companion object{
        const val PREFERENCES = "prefs"
        const val START_TIME_KEY = "startKey"
        const val PAUSE_TIME_KEY = "pauseKey"
        const val COUNTING_KEY = "countingKey"
    }
}