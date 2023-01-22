package com.kodego.app.timesmart_atimelogtracker.ui.personal_time_track.activityType

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.kodego.app.timesmart_atimelogtracker.R
import com.kodego.app.timesmart_atimelogtracker.databinding.PRowActivityTypesBinding
import com.kodego.app.timesmart_atimelogtracker.db.personal_time_track.activity_type_table.ActivityType

class ActivityTypeAdapter : RecyclerView.Adapter<ActivityTypeAdapter.ActivityTypeViewModel>() {

    private var activityTypeList = emptyList<ActivityType>()
    var onActivityTypeDelete : ((ActivityType, Int) -> Unit) ? = null

    inner class ActivityTypeViewModel(var binding : PRowActivityTypesBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityTypeViewModel {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = PRowActivityTypesBinding.inflate(layoutInflater, parent, false)
        return ActivityTypeViewModel(binding)
    }

    override fun onBindViewHolder(holder: ActivityTypeViewModel, @SuppressLint("RecyclerView") position: Int) {
        holder.binding.apply {
            var icon : Int = holder.itemView.resources.getIdentifier(activityTypeList[position].icon.toString(), "drawable", holder.itemView.context.packageName)
            tvActivityTypeName.text = activityTypeList[position].name
            ivActivityTypeIcon.setColorFilter(activityTypeList[position].color)

            if(activityTypeList[position].icon.isEmpty()){
                ivActivityTypeIcon.setImageResource(R.drawable.ic_image_place_holder)
            }else{
                ivActivityTypeIcon.setImageResource(icon)
            }
        }

        //Side Option Menu
        holder.binding.imgbActivityTypeOptionMenu.setOnClickListener(){
            val popupMenu = PopupMenu(holder.itemView.context, holder.binding.imgbActivityTypeOptionMenu)
            popupMenu.inflate(R.menu.activity_type_side_option_menu)

            popupMenu.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener{
                override fun onMenuItemClick(item: MenuItem?): Boolean {
                    when(item?.itemId){
                        R.id.editActivityType -> {
                            val action = PersonalActivityTypeFragmentDirections.actionNavPActivityTypesToUpdateActivityTypeDialogFragment(activityTypeList[position])
                            holder.itemView.findNavController().navigate(action)

                            return true
                        }
                        R.id.deleteActivityType -> {
                            onActivityTypeDelete?.invoke(activityTypeList[position], position)
                            return true
                        }
                    }
                    return false
                }
            })
            popupMenu.show()
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