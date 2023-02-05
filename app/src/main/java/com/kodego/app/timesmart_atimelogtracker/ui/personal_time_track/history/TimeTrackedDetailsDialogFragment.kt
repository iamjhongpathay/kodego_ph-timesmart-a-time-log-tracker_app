package com.kodego.app.timesmart_atimelogtracker.ui.personal_time_track.history

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.kodego.app.timesmart_atimelogtracker.R
import com.kodego.app.timesmart_atimelogtracker.databinding.DialogFragmentTimeTrackedDetailsBinding
import com.kodego.app.timesmart_atimelogtracker.db.personal_time_track.activity_type_table.ActivityType
import com.kodego.app.timesmart_atimelogtracker.db.personal_time_track.time_track_table.TimeTrack
import com.kodego.app.timesmart_atimelogtracker.db.personal_time_track.time_track_table.TimeTrackViewModel
import com.kodego.app.timesmart_atimelogtracker.ui.personal_time_track.activityType.ActivityTypeAdapter
import java.text.SimpleDateFormat


class TimeTrackedDetailsDialogFragment : BottomSheetDialogFragment() {

    lateinit var binding: DialogFragmentTimeTrackedDetailsBinding
    private val args by navArgs<TimeTrackedDetailsDialogFragmentArgs>()
    var defaultColor = 0

    lateinit var timeTrackViewModel: TimeTrackViewModel

    lateinit var historyTimeTrackedAdapter: HistoryTimeTrackedAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DialogFragmentTimeTrackedDetailsBinding.inflate(layoutInflater)
        timeTrackViewModel = ViewModelProvider(this)[TimeTrackViewModel::class.java]
        historyTimeTrackedAdapter = HistoryTimeTrackedAdapter()

        val icon: Int = resources.getIdentifier(args.currentTimeTracked.icon.toString(), "drawable", requireContext().packageName)
        var dateFormat = SimpleDateFormat("EEEE, dd MMM yyyy")
        val timeFormat = SimpleDateFormat("hh:mm a")
        //set the current value by user selected from recyclerview
        binding.tvActivityTypeNameDetailsHistory.text = args.currentTimeTracked.activityTypeName
        binding.ivIconDetailsHistory.setImageResource(icon)
        defaultColor = args.currentTimeTracked.color
        binding.ivIconDetailsHistory.setColorFilter(defaultColor)
        binding.etCommentDetailsHistory.setText(args.currentTimeTracked.comment)
        binding.tvStartedOnDetailsHistory.text = dateFormat.format(args.currentTimeTracked.dateTimeStarted)
        binding.tvStartedTimeDetailsHistory.text = timeFormat.format(args.currentTimeTracked.dateTimeStarted)
        binding.tvEndedDateDetailsHistory.text = dateFormat.format(args.currentTimeTracked.dateTimeStopped)
        binding.tvEndedTimeDetailsHistory.text = timeFormat.format(args.currentTimeTracked.dateTimeStopped)
        binding.tvTimeTrackedDetailsHistory.text = timeStringFromLong(args.currentTimeTracked.timeTracked)

        binding.btnSaveTimeTrackedHistoryDetails.setOnClickListener(){
            update()
        }

        binding.btnDeleteTimeTrackedHistoryDetails.setOnClickListener(){
            delete()
        }

        return binding.root
    }



    private fun timeStringFromLong(ms: Long): String
    {
        val seconds = (ms / 1000) % 60
        val minutes = (ms / (1000 * 60) % 60)
        val hours = (ms / (1000 * 60 * 60) % 24)
        return makeTimeString(hours, minutes, seconds)
    }

    private fun makeTimeString(hours: Long, minutes: Long, seconds: Long): String
    {
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }

    private fun update(){

        var comment : String = binding.etCommentDetailsHistory.text.toString()

        timeTrackViewModel.updateTimeTracked(comment, args.currentTimeTracked.id)
        dismiss()
    }

    private fun delete(){
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){_,_ ->

            timeTrackViewModel.deleteTimeTracked(args.currentTimeTracked)
            val bottomNavView: BottomNavigationView = activity?.findViewById(R.id.nav_view)!!
            Snackbar.make(requireActivity().findViewById(android.R.id.content), "Activity deleted from history", Snackbar.ANIMATION_MODE_SLIDE).apply {
                anchorView = bottomNavView
            }.show()
            dismiss()

        }
        builder.setNegativeButton("No"){_,_ -> }
        builder.setTitle("Remove")
        builder.setMessage("Remove this activity?")
            .show()
    }
}