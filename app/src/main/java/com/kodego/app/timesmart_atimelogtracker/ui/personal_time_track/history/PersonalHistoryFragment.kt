package com.kodego.app.timesmart_atimelogtracker.ui.personal_time_track.history

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.kodego.app.timesmart_atimelogtracker.R
import com.kodego.app.timesmart_atimelogtracker.databinding.PFragmentHistoryBinding
import com.kodego.app.timesmart_atimelogtracker.db.personal_time_track.time_track_table.TimeTrack
import com.kodego.app.timesmart_atimelogtracker.db.personal_time_track.time_track_table.TimeTrackViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class PersonalHistoryFragment : Fragment(), DatePickerDialog.OnDateSetListener {

    private lateinit var binding : PFragmentHistoryBinding
    private val calendar = Calendar.getInstance()
    @SuppressLint("SimpleDateFormat")
    private val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())

    private lateinit var historyTimeTrackedAdapter: HistoryTimeTrackedAdapter
    private lateinit var timeTrackViewModel: TimeTrackViewModel

    lateinit var pieChart : PieChart
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PFragmentHistoryBinding.inflate(layoutInflater)

        timeTrackViewModel = ViewModelProvider(this)[TimeTrackViewModel::class.java]
        viewHistory()

        binding.tvDate.setOnClickListener(){
            val datePicker = DatePickerDialog(
                requireContext(),
                this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
            ).show()
        }

        pieChart = binding.pieChart
        setupPieChart()
        loadPieChartData()

        binding.btnHistoryList.setImageResource(R.drawable.ic_list_active)
        binding.btnPieChartHIstory.setImageResource(R.drawable.ic_pie_chart)

        return binding.root
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        calendar.set(year, month, dayOfMonth)
        viewHistory()
    }

    private fun displayFormattedDate(timeStamp : Long) {
        binding.tvDate.text = dateFormat.format(timeStamp)
    }

    private fun viewHistory(){
        //initializing ActivityTypeAdapter
        historyTimeTrackedAdapter = HistoryTimeTrackedAdapter()
        //Binding the activityTypeAdapter to recyclerview
        binding.recyclerViewHistory.adapter = historyTimeTrackedAdapter
        binding.recyclerViewHistory.layoutManager = LinearLayoutManager(requireContext())

        displayFormattedDate(calendar.timeInMillis).toString()

        //ActivityTypeViewModel
        timeTrackViewModel.readTimeTrackByDate(binding.tvDate.text.toString()).observe(viewLifecycleOwner) { timeTracked ->


            binding.btnHistoryList.setOnClickListener(){
                binding.btnHistoryList.setImageResource(R.drawable.ic_list_active)
                binding.btnPieChartHIstory.setImageResource(R.drawable.ic_pie_chart)
                if(timeTracked.isNotEmpty()){
                    binding.recyclerViewHistory.visibility = View.VISIBLE
                    binding.pieChart.visibility = View.GONE
                    binding.tvNoHistory.visibility = View.GONE
                    binding.tvNoHistory2.visibility = View.GONE
                    binding.ivNothingFound.visibility = View.GONE
                }else{
                    binding.recyclerViewHistory.visibility = View.GONE
                    binding.tvNoHistory.visibility = View.VISIBLE
                    binding.tvNoHistory2.visibility = View.VISIBLE
                    binding.ivNothingFound.visibility = View.VISIBLE
                }
            }

            binding.btnPieChartHIstory.setOnClickListener(){
                binding.btnHistoryList.setImageResource(R.drawable.ic_list)
                binding.btnPieChartHIstory.setImageResource(R.drawable.ic_pie_chart_active)
                setupPieChart()
                loadPieChartData()
                if(timeTracked.isNotEmpty()){
                    binding.pieChart.visibility = View.VISIBLE
                    binding.recyclerViewHistory.visibility = View.GONE
                    binding.tvNoHistory.visibility = View.GONE
                    binding.tvNoHistory2.visibility = View.GONE
                    binding.ivNothingFound.visibility = View.GONE
                }else{
                    binding.pieChart.visibility = View.GONE
                    binding.tvNoHistory.visibility = View.VISIBLE
                    binding.tvNoHistory2.visibility = View.VISIBLE
                    binding.ivNothingFound.visibility = View.VISIBLE
                }
            }

            if(timeTracked.isNotEmpty()){
                binding.recyclerViewHistory.visibility = View.VISIBLE
                binding.pieChart.visibility = View.GONE
                binding.tvNoHistory.visibility = View.GONE
                binding.tvNoHistory2.visibility = View.GONE
                binding.ivNothingFound.visibility = View.GONE
            }else{
                binding.recyclerViewHistory.visibility = View.GONE
                binding.pieChart.visibility = View.GONE
                binding.tvNoHistory.visibility = View.VISIBLE
                binding.tvNoHistory2.visibility = View.VISIBLE
                binding.ivNothingFound.visibility = View.VISIBLE
            }

            historyTimeTrackedAdapter.setData(timeTracked)
        }

        var timeTracked = timeStringFromLong(timeTrackViewModel.getTotalTimeTracked(binding.tvDate.text.toString()))
        binding.tvTimeTracked.text = "Time tracked: $timeTracked"
        Log.e("SUM Time Tracked", timeTrackViewModel.getTotalTimeTracked(binding.tvDate.text.toString()).toString())
    }

    private fun timeStringFromLong(ms: Long): String
    {
        val minutes = (ms / (1000 * 60) % 60)
        val hours = (ms / (1000 * 60 * 60) % 24)
        return makeTimeString(hours, minutes)
    }

    private fun makeTimeString(hours: Long, minutes: Long): String
    {
        return String.format("%02dh %02dmin", hours, minutes)
    }

    private fun setupPieChart(){
        pieChart.isDrawHoleEnabled = true
        pieChart.setUsePercentValues(true)
        pieChart.setEntryLabelTextSize(12F)
        pieChart.setEntryLabelColor(Color.WHITE)
        pieChart.centerText = "Time spent on \n daily activities"
        pieChart.setCenterTextSize(18F)
        pieChart.description.isEnabled = false

        val l: Legend = pieChart.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.textSize = 12F
        l.setDrawInside(false)
        l.isEnabled = false

    }

    private fun loadPieChartData(){
        var colors = ArrayList<Int>()
        var entries = ArrayList<PieEntry>()
        var list = timeTrackViewModel.getAllTimeTrackedByDate(binding.tvDate.text.toString())
        for (item in list){
            entries.add(PieEntry(item.timeTracked.toFloat(), "${item.activityTypeName}(${timeStringFromLong(item.timeTracked)})"))
        }

        //Colors

        for(item in list){
            colors.add(item.color)
        }

        for (item in list){
            colors.add(item.color)
        }
        //Colors of each entries
        var dataSet: PieDataSet = PieDataSet(entries, "")
        dataSet.colors = colors

        //Pie data to pie chart
        var data : PieData = PieData(dataSet)
        data.setDrawValues(true)
        data.setValueFormatter(PercentFormatter(pieChart))
        data.setValueTextSize(12f)
        data.setValueTextColor(Color.WHITE)
        pieChart.data = data
        pieChart.invalidate()
        pieChart.animateY(1400, Easing.EaseInOutQuad);
    }

}