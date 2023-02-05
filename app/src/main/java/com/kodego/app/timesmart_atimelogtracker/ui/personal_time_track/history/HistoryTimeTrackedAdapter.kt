package com.kodego.app.timesmart_atimelogtracker.ui.personal_time_track.history

import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.kodego.app.timesmart_atimelogtracker.databinding.PRowHistoryTimeTrackListBinding
import com.kodego.app.timesmart_atimelogtracker.db.personal_time_track.activity_type_table.ActivityType
import com.kodego.app.timesmart_atimelogtracker.db.personal_time_track.time_track_table.TimeTrack
import com.kodego.app.timesmart_atimelogtracker.ui.personal_time_track.activityType.PersonalActivityTypeFragmentDirections
import java.text.SimpleDateFormat
import java.util.*

class HistoryTimeTrackedAdapter() : RecyclerView.Adapter<HistoryTimeTrackedAdapter.PersonalHistoryTimeTrackedViewModel>() {

    private var timeTracked = emptyList<TimeTrack>()

    inner class PersonalHistoryTimeTrackedViewModel(var binding : PRowHistoryTimeTrackListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PersonalHistoryTimeTrackedViewModel {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = PRowHistoryTimeTrackListBinding.inflate(layoutInflater, parent, false)
        return PersonalHistoryTimeTrackedViewModel(binding)
    }

    override fun getItemCount(): Int {
        return timeTracked.size
    }

    override fun onBindViewHolder(holder: PersonalHistoryTimeTrackedViewModel, position: Int) {
        holder.binding.apply {
            val icon: Int = holder.itemView.resources.getIdentifier(timeTracked[position].icon.toString(), "drawable", holder.itemView.context.packageName)
            val timeFormat = SimpleDateFormat("h:mm a")
            val startTime = timeFormat.format(timeTracked[position].dateTimeStarted)
            val endTime = timeFormat.format(timeTracked[position].dateTimeStopped)

            tvActivityTypeNameHistory.text = timeTracked[position].activityTypeName
            ivActivityTypeIconHistory.setColorFilter(timeTracked[position].color)
            ivActivityTypeIconHistory.setImageResource(icon)
            tvTimeStartEndHistory.text = "$startTime - $endTime"
            tvTrackedTime.text = timeStringFromLong(timeTracked[position].timeTracked)
            tvComment.text = timeTracked[position].comment
        }
        holder.itemView.setOnClickListener(){
            val action = PersonalHistoryFragmentDirections.actionNavPHistoryToTimeTrackedDetailsDialogFragment(timeTracked[position])
            holder.itemView.findNavController().navigate(action)
        }
    }

    fun setData(item: MutableList<TimeTrack>){
        this.timeTracked = item
        notifyDataSetChanged()
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
        return String.format("%02dh %02dmin", hours, minutes, seconds)
    }

}

