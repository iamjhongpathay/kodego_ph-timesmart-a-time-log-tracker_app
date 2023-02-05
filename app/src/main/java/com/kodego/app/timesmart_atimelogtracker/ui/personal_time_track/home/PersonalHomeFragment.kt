package com.kodego.app.timesmart_atimelogtracker.ui.personal_time_track.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.kodego.app.timesmart_atimelogtracker.R
import com.kodego.app.timesmart_atimelogtracker.databinding.PFragmentHomeBinding
import com.kodego.app.timesmart_atimelogtracker.db.personal_time_track.activity_type_table.ActivityTypeViewModel
import com.kodego.app.timesmart_atimelogtracker.db.personal_time_track.time_track_table.TimeTrack
import com.kodego.app.timesmart_atimelogtracker.db.personal_time_track.time_track_table.TimeTrackViewModel
import java.text.SimpleDateFormat
import java.util.*

class PersonalHomeFragment : Fragment() {

    private lateinit var binding : PFragmentHomeBinding

    private lateinit var homeActivityTypeListAdapter: HomeActivityTypeListAdapter
    private lateinit var activityTypeViewModel: ActivityTypeViewModel
    private  lateinit var timeTrackViewModel: TimeTrackViewModel

    private lateinit var dataHelper: DataHelper
    private val timer = Timer()

    lateinit var sharedPreferences: SharedPreferences

    private lateinit var iconString : String
    lateinit var dateTimeStarted: Date
    var colorInt : Int = 0

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //instantiate
        binding = PFragmentHomeBinding.inflate(layoutInflater)
        activityTypeViewModel = ViewModelProvider(this)[ActivityTypeViewModel::class.java]
        timeTrackViewModel = ViewModelProvider(this)[TimeTrackViewModel::class.java]
        dataHelper = DataHelper(requireContext()) //Shared Preferences StopWatch
        sharedPreferences = requireContext().getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)

        viewActivityTypeList()
        loadSharedPref()

        binding.btnStop.setOnClickListener(){
            var dateFormat = SimpleDateFormat("MMM dd, yyyy")
            var timeFormat = SimpleDateFormat("hh:mm a")

            val activityTypeName: String = binding.tvActivityTypeName2.text.toString()
            val color: Int = colorInt
            val icon: String = iconString
            val dateTimeStarted = dateTimeStarted
            val dateCreated: String = dateFormat.format(Date())
            val timeCreated: String = binding.tvTimeStarted.text.toString()
            val timeStopped: String = timeFormat.format(Date())
            val dateTimeStopped: Date = Date()
            val timeTracked: Long = dateTimeStopped.time - dateTimeStarted.time
            val comment: String? = null

            //save to db
            val saveTimeTrack = TimeTrack(0, activityTypeName, icon, color, dateTimeStarted, dateCreated, timeCreated, timeStopped, dateTimeStopped, timeTracked, comment)
            saveToHistory(saveTimeTrack)


            clearSharedPref()
            resetAction()
            binding.btnStop.isEnabled = false
            binding.imageView5.visibility = View.VISIBLE
        }

        if(dataHelper.timerCounting()) {
            startTimer()
            binding.btnStop.isEnabled = true
            binding.imageView5.visibility = View.GONE
        }
        else {
            if(dataHelper.startTime() != null && dataHelper.pauseTime() != null) {
                val time = Date().time - calcRestartTime().time
                binding.tvTimerCounting.text = timeStringFromLong(time)
                binding.btnStop.isEnabled = true
                binding.imageView5.visibility = View.GONE
            }
            binding.btnStop.isEnabled = false
            binding.imageView5.visibility = View.VISIBLE
        }
        timer.scheduleAtFixedRate(TimeTask(), 0, 500)

        return binding.root
    }



    private inner class TimeTask: TimerTask() {
        override fun run() {
            if(dataHelper.timerCounting()) {
                val time = Date().time - dataHelper.startTime()!!.time
                requireActivity().runOnUiThread(){
                    binding.tvTimerCounting.text = timeStringFromLong(time)
                }
            }
        }
    }

    private fun resetAction() {
        dataHelper.setPauseTime(null)
        dataHelper.setStartTime(null)
        dataHelper.setTimerCounting(false)
        binding.tvTimerCounting.text = timeStringFromLong(0)
    }

    private fun startTimer() {

        if (dataHelper.timerCounting()) {
            dataHelper.setPauseTime(Date())
        } else {
            if (dataHelper.pauseTime() != null) {
                dataHelper.setStartTime(calcRestartTime())
                dataHelper.setPauseTime(null)

            } else {
                dataHelper.setStartTime(Date())
            }
            dataHelper.setTimerCounting(true)
        }
    }

    private fun calcRestartTime(): Date {
        val diff = dataHelper.startTime()!!.time - dataHelper.pauseTime()!!.time
        return Date(System.currentTimeMillis() + diff)
    }

    private fun timeStringFromLong(ms: Long): String {
        val seconds = (ms / 1000) % 60
        val minutes = (ms / (1000 * 60) % 60)
        val hours = (ms / (1000 * 60 * 60) % 24)
        return makeTimeString(hours, minutes, seconds)
    }

    private fun makeTimeString(hours: Long, minutes: Long, seconds: Long): String {
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }

    @SuppressLint("SimpleDateFormat", "DiscouragedApi")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun viewActivityTypeList(){
        homeActivityTypeListAdapter = HomeActivityTypeListAdapter()

        //Activity Types List recyclerview
        binding.recyclerViewPersonalHomeActivityTypesList.adapter = homeActivityTypeListAdapter
        binding.recyclerViewPersonalHomeActivityTypesList.layoutManager = GridLayoutManager(requireContext(), 4)
        //ActivityTypeViewModel
        activityTypeViewModel.readAllDataActivityTypes.observe(viewLifecycleOwner, Observer { activity ->

            if(activity.isEmpty()){
                binding.recyclerViewPersonalHomeActivityTypesList.visibility = View.GONE
                binding.tvNoActivityTypeHome.visibility = View.VISIBLE
                binding.tvNoActivityTypmeHome2.visibility = View.VISIBLE
            }else{
                binding.recyclerViewPersonalHomeActivityTypesList.visibility = View.VISIBLE
                binding.tvNoActivityTypeHome.visibility = View.GONE
                binding.tvNoActivityTypmeHome2.visibility = View.GONE
            }
            homeActivityTypeListAdapter.setData(activity)
        })

        //Start StopWatch and Save to DB when the user clicked one of the activity type from recyclerview
        homeActivityTypeListAdapter.onActivityTypeClicked = {
            if(dataHelper.timerCounting()){
                val activityRunning = binding.tvActivityTypeName2.text
                val bottomNavView: BottomNavigationView = activity?.findViewById(R.id.nav_view)!!
                Snackbar.make(requireActivity().findViewById(android.R.id.content), "$activityRunning Activity is running", Snackbar.ANIMATION_MODE_SLIDE).apply {
                    anchorView = bottomNavView
                }.show()

            }else{
                //Date and Time Format
                var timeFormat = SimpleDateFormat("hh:mm a")

                //Get the data from activity types
                val activityTypeName: String = it.name
                val color: Int = it.color
                val icon: String = it.icon
                val timeCreated: String = timeFormat.format(Date())
                dateTimeStarted = Date()

                //save to shared preferences
                val btnStop = R.drawable.ic_stop
                saveToSharedPref(activityTypeName, icon, color, timeCreated, btnStop, dateTimeStarted)

                startTimer()
                binding.btnStop.isEnabled = true
                binding.imageView5.visibility = View.GONE
            }
        }
    }

    private fun saveToHistory(timeTrack: TimeTrack){
        timeTrackViewModel.addTimeTrack(timeTrack)
    }

    private fun saveToSharedPref(activityType: String, icon: String, color: Int, timeCreated: String, stopButton: Int, dateTimeStarted: Date){
        var displayIcon : Int = resources.getIdentifier(icon, "drawable", requireActivity().packageName)

        binding.tvActivityTypeName2.text = activityType
        binding.ivActivityTypeIcon2.setImageResource(displayIcon)
        binding.ivActivityTypeIcon2.setColorFilter(color)
        binding.tvTimeStarted.text = timeCreated
        binding.btnStop.setImageResource(stopButton)
        iconString = icon
        colorInt = color
        this.dateTimeStarted = dateTimeStarted

        val editor : SharedPreferences.Editor = sharedPreferences.edit()
        editor.apply(){
            putString("ACTIVITY_TYPE", activityType)
            putInt("ICON", displayIcon)
            putString("ICON_STRING", icon)
            putInt("COLOR", color)
            putString("TIME_CREATED", timeCreated)
            putLong("DATE_TIME_STARTED", dateTimeStarted.time)
            putInt("BTNSTOP", stopButton)
        }.apply()
    }

    private fun loadSharedPref(){
        val savedActivityType: String? = sharedPreferences.getString("ACTIVITY_TYPE", null)
        val savedIcon: Int = sharedPreferences.getInt("ICON", 0)
        val savedIconString: String? = sharedPreferences.getString("ICON_STRING", null)
        val savedColor: Int = sharedPreferences.getInt("COLOR", 0)
        val savedTimeCreated: String? = sharedPreferences.getString("TIME_CREATED", null)
        val savedDateTimeStarted: Long = sharedPreferences.getLong("DATE_TIME_STARTED", 0L)
        val btnStop: Int = sharedPreferences.getInt("BTNSTOP", R.drawable.ic_stop_disabled)

        binding.tvActivityTypeName2.text = savedActivityType
        binding.ivActivityTypeIcon2.setImageResource(savedIcon)
        if (savedIconString != null) {
            iconString = savedIconString
        }
        binding.ivActivityTypeIcon2.setColorFilter(savedColor)
        colorInt = savedColor
        binding.tvTimeStarted.text = savedTimeCreated
        dateTimeStarted = Date(savedDateTimeStarted)
        binding.btnStop.setImageResource(btnStop)
    }

    private fun clearSharedPref(){
        sharedPreferences.edit().clear().apply()
        binding.tvActivityTypeName2.text = null
        binding.ivActivityTypeIcon2.setImageResource(0)
        iconString = null.toString()
        binding.ivActivityTypeIcon2.setColorFilter(0)
        colorInt = 0
        binding.tvTimeStarted.text = null
        binding.tvTimerCounting.text = getString(R.string.tv_time_counting_place_holder)
        binding.btnStop.setImageResource(R.drawable.ic_stop_disabled)
    }

}