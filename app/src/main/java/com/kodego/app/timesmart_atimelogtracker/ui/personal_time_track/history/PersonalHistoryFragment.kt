package com.kodego.app.timesmart_atimelogtracker.ui.personal_time_track.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kodego.app.timesmart_atimelogtracker.databinding.PFragmentHistoryBinding

class PersonalHistoryFragment : Fragment() {

    private lateinit var binding : PFragmentHistoryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       binding = PFragmentHistoryBinding.inflate(layoutInflater)

        return binding.root
    }

}