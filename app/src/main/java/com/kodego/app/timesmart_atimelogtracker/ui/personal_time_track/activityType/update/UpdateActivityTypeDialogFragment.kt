package com.kodego.app.timesmart_atimelogtracker.ui.personal_time_track.activityType.update

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kodego.app.timesmart_atimelogtracker.R
import com.kodego.app.timesmart_atimelogtracker.databinding.DialogFragmentUpdateActivityTypeBinding
import com.kodego.app.timesmart_atimelogtracker.db.personal_time_track.activity_type_table.ActivityType
import com.kodego.app.timesmart_atimelogtracker.db.personal_time_track.activity_type_table.ActivityTypeViewModel
import com.kodego.app.timesmart_atimelogtracker.ui.personal_time_track.activityType.add.CustomDropdownAdapter
import yuku.ambilwarna.AmbilWarnaDialog
import java.util.*


class UpdateActivityTypeDialogFragment : BottomSheetDialogFragment() {

    private lateinit var binding : DialogFragmentUpdateActivityTypeBinding
    private val args by navArgs<UpdateActivityTypeDialogFragmentArgs>()
    lateinit var activityTypeViewModel : ActivityTypeViewModel
    var defaultColor = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DialogFragmentUpdateActivityTypeBinding.inflate(layoutInflater)
        activityTypeViewModel = ViewModelProvider(this)[ActivityTypeViewModel::class.java]

        //Exposed dropdown menu
        val activityTypeIconNames = resources.getStringArray(R.array.icon_names) //getting the list of icon names in string.xml
        activityTypeIconNames.sort() //ascending order
        val arrayAdapter = CustomDropdownAdapter(requireContext(), activityTypeIconNames) //CustomDropDownAdapter
        binding.spinnerEditActivityType.adapter = arrayAdapter

        val currentIcon: String = args.selectedActivityType.icon
        val spinnerPosition: Int = arrayAdapter.getPosition(currentIcon)
        binding.spinnerEditActivityType.setSelection(spinnerPosition)

        //set the current value by user selected from recyclerview
        binding.etEditActivityTypeName.setText(args.selectedActivityType.name)
        defaultColor = args.selectedActivityType.color
        binding.btnEditChooseColor.setBackgroundColor(defaultColor)


        binding.btnEditChooseColor.setOnClickListener(){
            openColorPicker()
        }

        binding.btnCancelEditActivityType.setOnClickListener(){
            dismiss()
        }

        binding.btnUpdateActivityType.setOnClickListener(){
            update()
        }

        return binding.root
    }




    private fun openColorPicker() {
        var colorPicker = AmbilWarnaDialog(requireContext(), defaultColor, object :
            AmbilWarnaDialog.OnAmbilWarnaListener {
            override fun onCancel(dialog: AmbilWarnaDialog?) {

            }
            override fun onOk(dialog: AmbilWarnaDialog?, color: Int) {
                defaultColor = color
                binding.btnEditChooseColor.setBackgroundColor(defaultColor)
            }
        })
        colorPicker.show()
    }

    private fun update(){

        var activityTypeName : String = binding.etEditActivityTypeName.text.toString()
        var icon : String = binding.spinnerEditActivityType.selectedItem.toString()
            .replaceFirstChar { it.lowercase(Locale.ROOT) }
        var iconColor : Int = defaultColor

        val updateActivityType = ActivityType(args.selectedActivityType.id, activityTypeName, icon, iconColor)
        activityTypeViewModel.updateActivityType(updateActivityType)

        dismiss()
    }
}