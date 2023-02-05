package com.kodego.app.timesmart_atimelogtracker.ui.personal_time_track.home

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.kodego.app.timesmart_atimelogtracker.databinding.PRowHomeStopWatchBinding
import com.kodego.app.timesmart_atimelogtracker.db.personal_time_track.time_track_table.TimeTrack
import java.text.SimpleDateFormat
import java.util.*

class HomeStopWatchAdapter(var stopWatchList : MutableList<TimeTrack>) : RecyclerView.Adapter<HomeStopWatchAdapter.PersonalHomeStopWatchViewModel>() {

    var onPauseClicked : ((TimeTrack) -> Unit)? = null
    var onStopClicked : ((TimeTrack, Int) -> Unit)? = null


    inner class PersonalHomeStopWatchViewModel(var binding : PRowHomeStopWatchBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PersonalHomeStopWatchViewModel {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = PRowHomeStopWatchBinding.inflate(layoutInflater, parent, false)
        return PersonalHomeStopWatchViewModel(binding)
    }

    override fun getItemCount(): Int {
        return stopWatchList.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: PersonalHomeStopWatchViewModel, position: Int) {
        holder.binding.apply {

            var icon : Int = holder.itemView.resources.getIdentifier(stopWatchList[position].icon.toString(), "drawable", holder.itemView.context.packageName)
            var simpleDateTimeFormat = SimpleDateFormat("hh:mm a")
            var time = simpleDateTimeFormat.format(stopWatchList[position].dateTimeStarted)

            tvActivityTypeNamePH.text = stopWatchList[position].activityTypeName
            ivActivityTypeIconPH.setImageResource(icon)
            ivActivityTypeIconPH.setColorFilter(stopWatchList[position].color)
            tvTimeStartedPH.text = time

            imgbPause.setOnClickListener(){
                onPauseClicked?.invoke(stopWatchList[position])
            }

            imgbStop.setOnClickListener(){
                onStopClicked?.invoke(stopWatchList[position], position)
            }
        }

    }

}