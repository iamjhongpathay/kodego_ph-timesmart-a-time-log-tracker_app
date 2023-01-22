package com.kodego.app.timesmart_atimelogtracker.ui.personal_time_track.activityType.add

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.kodego.app.timesmart_atimelogtracker.R
import java.util.*

class CustomDropdownAdapter(context: Context, list: Array<String>) :
    ArrayAdapter<String>(context, R.layout.dropdown_icons_activity_type, list) {

    var getContext : Context
    var iconList : Array<String>

    init{
        this.getContext = context
        this.iconList = list
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    fun getCustomView(position: Int, convertView: View?, parent: ViewGroup) : View {
        //inflate the dropdown_activity_type_icons_b layout to CustomDropDownAdapter
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val row : View = inflater.inflate(R.layout.dropdown_icons_activity_type, parent, false)

        val iconName : TextView = row.findViewById(R.id.tvDropDownIconName2)
        val icon : ImageView = row.findViewById(R.id.ivDropdownIcon)

        //Long way declaration
//        val res : Resources = context.resources
//        val drawableName = iconList[position]
//        val resID : Int = context.resources.getIdentifier(iconList[position], "drawable", context.packageName)
//        val drawable : Drawable = res.getDrawable(resID)

        val resID : Int = context.resources.getIdentifier(iconList[position], "drawable", context.packageName)

        iconName.text = iconList[position].replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.ROOT
            ) else it.toString()
        }.replace("_", " ")

        icon.setImageResource(resID)

        return row
    }

}