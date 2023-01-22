package com.kodego.app.timesmart_atimelogtracker.ui.personal_time_track.reminder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kodego.app.timesmart_atimelogtracker.databinding.PFragmentReminderBinding

class NotificationsFragment : Fragment() {

    private lateinit var binding : PFragmentReminderBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PFragmentReminderBinding.inflate(layoutInflater)

        return binding.root
    }

}