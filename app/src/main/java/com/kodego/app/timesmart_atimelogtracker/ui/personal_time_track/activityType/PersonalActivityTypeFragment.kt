package com.kodego.app.timesmart_atimelogtracker.ui.personal_time_track.activityType

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kodego.app.timesmart_atimelogtracker.R
import com.kodego.app.timesmart_atimelogtracker.databinding.PFragmentActivityTypeBinding
import com.kodego.app.timesmart_atimelogtracker.db.personal_time_track.activity_type_table.ActivityType
import com.kodego.app.timesmart_atimelogtracker.db.personal_time_track.activity_type_table.ActivityTypeViewModel

class PersonalActivityTypeFragment : Fragment() {

    private lateinit var binding : PFragmentActivityTypeBinding
    private lateinit var activityTypeAdapter: ActivityTypeAdapter
    private lateinit var activityTypeViewModel: ActivityTypeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = PFragmentActivityTypeBinding.inflate(layoutInflater)

        activityTypeViewModel = ViewModelProvider(this)[ActivityTypeViewModel::class.java]
        viewActivities()

        binding.fabAddActivityType.setOnClickListener(){
            findNavController().navigate(R.id.action_nav_p_activityTypes_to_addActivityTypeDialogFragment)

            //var dialog = AddActivityTypeDialogFragment()
            //dialog.show(childFragmentManager, "customDialog")
        }

        return binding.root
    }

    //Display the List of Activity Types from DB to RecyclerView
    private fun viewActivities(){
        //initializing ActivityTypeAdapter
        activityTypeAdapter = ActivityTypeAdapter()
        //Binding the activityTypeAdapter to recyclerview
        binding.recyclerViewPersonalActivityTypes.adapter = activityTypeAdapter
        binding.recyclerViewPersonalActivityTypes.layoutManager = LinearLayoutManager(requireContext())

        //ActivityTypeViewModel
        activityTypeViewModel.readAllDataActivityTypes.observe(viewLifecycleOwner, Observer { activity ->
            activityTypeAdapter.setData(activity)
        })
        //delete activity type
        delete()
    }

    private fun delete(){
        activityTypeAdapter.onActivityTypeDelete = { item: ActivityType, position: Int ->
            activityTypeViewModel.deleteActivityType(item)
        }
    }
}