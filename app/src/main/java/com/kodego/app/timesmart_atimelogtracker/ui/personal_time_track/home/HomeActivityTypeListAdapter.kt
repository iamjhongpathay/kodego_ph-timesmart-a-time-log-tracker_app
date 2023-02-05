package com.kodego.app.timesmart_atimelogtracker.ui.personal_time_track.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.kodego.app.timesmart_atimelogtracker.R
import com.kodego.app.timesmart_atimelogtracker.databinding.PRowHomeActivityTypesListBinding
import com.kodego.app.timesmart_atimelogtracker.db.personal_time_track.activity_type_table.ActivityType

class HomeActivityTypeListAdapter() : RecyclerView.Adapter<HomeActivityTypeListAdapter.PersonalHomeActivityTypesViewModel>() {

    private var activityTypeList = emptyList<ActivityType>()
    var onActivityTypeClicked : ((ActivityType) -> Unit)? = null

    inner class PersonalHomeActivityTypesViewModel(var binding : PRowHomeActivityTypesListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonalHomeActivityTypesViewModel {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = PRowHomeActivityTypesListBinding.inflate(layoutInflater, parent, false)
        return PersonalHomeActivityTypesViewModel(binding)
    }

    override fun onBindViewHolder(holder: PersonalHomeActivityTypesViewModel, position: Int) {

        holder.binding.apply {
            var icon : Int = holder.itemView.resources.getIdentifier(activityTypeList[position].icon.toString(), "drawable", holder.itemView.context.packageName)
            tvHomeActivityTypeName.text = activityTypeList[position].name
            ivHomeActivityTypeIcon.setColorFilter(activityTypeList[position].color)

            if(activityTypeList[position].icon.isEmpty()){
                ivHomeActivityTypeIcon.setImageResource(R.drawable.ic_image_place_holder)
            }else{
                ivHomeActivityTypeIcon.setImageResource(icon)
            }
        }

        holder.itemView.setOnClickListener(){
            onActivityTypeClicked?.invoke(activityTypeList[position])
        }
    }

    override fun getItemCount(): Int {
        return activityTypeList.size
    }

    //refresh the recyclerview when new or updated data will occur
    fun setData(item: MutableList<ActivityType>){
        this.activityTypeList = item
        notifyDataSetChanged()
    }
}