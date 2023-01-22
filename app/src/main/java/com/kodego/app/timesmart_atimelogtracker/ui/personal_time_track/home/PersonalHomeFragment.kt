package com.kodego.app.timesmart_atimelogtracker.ui.personal_time_track.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.kodego.app.timesmart_atimelogtracker.databinding.PFragmentHomeBinding
import com.kodego.app.timesmart_atimelogtracker.db.personal_time_track.activity_type_table.ActivityTypeViewModel

class PersonalHomeFragment : Fragment() {

    private lateinit var binding : PFragmentHomeBinding
    lateinit var activityTypeViewModel: ActivityTypeViewModel
    private lateinit var activityTypeListAdapter: ActivityTypeListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PFragmentHomeBinding.inflate(layoutInflater)
        activityTypeViewModel = ViewModelProvider(this)[ActivityTypeViewModel::class.java]
        viewActivityTypes()

        return binding.root
    }



    private fun viewActivityTypes(){
        activityTypeListAdapter = ActivityTypeListAdapter()

        binding.recyclerViewPersonalHomeActivityTypesList.adapter = activityTypeListAdapter
        binding.recyclerViewPersonalHomeActivityTypesList.layoutManager = GridLayoutManager(requireContext(), 4)

        //ActivityTypeViewModel
        activityTypeViewModel.readAllDataActivityTypes.observe(viewLifecycleOwner, Observer { activity ->
            activityTypeListAdapter.setData(activity)
        })
    }
}