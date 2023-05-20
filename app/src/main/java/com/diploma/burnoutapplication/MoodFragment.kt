package com.diploma.burnoutapplication

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.diploma.burnoutapplication.databinding.FragmentMoodBinding
import com.diploma.burnoutapplication.list.TodoApplication
import com.diploma.burnoutapplication.mood.*
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*
// TODO: Довести до ума отображение графика и как в расписании и списке дел придумать, что отобразить пока данных нет
class MoodFragment : Fragment(), MoodItemClickListener {
    private var _binding: FragmentMoodBinding?= null
    private val binding get() = _binding!!
    lateinit var arrayList: ArrayList<Entry>
    private val moodViewModel: MoodViewModel by activityViewModels {
        MoodItemModelFactory((requireActivity().application as TodoApplication).repository3)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoodBinding.inflate(inflater, container, false)

        binding.addMoodButton.setOnClickListener{
            NewMoodCardFragment().show(parentFragmentManager, "newMoodTag")
        }

        setRecyclerView()

        return binding.root
    }

    private fun setRecyclerView()
    {
        val fragment = this
        arrayList = ArrayList()
        val chart = binding.lineChart
        moodViewModel.moodItems.observe(viewLifecycleOwner){
            binding.moodRecyclerView.apply {
                if(isAdded) layoutManager = LinearLayoutManager(requireActivity())
                adapter = MoodItemAdapter(it, fragment)
            }
            val sort1 = it.groupBy({it.dateAddedString}) {it.mark}

            for(i in sort1){
                val y = i.value.sum()/i.value.size.toDouble()
                val date = LocalDate.parse(i.key, DateTimeFormatter.ofPattern("dd MMM yyyy").withLocale(
                    Locale("ru")
                ))
                val selectedDateToLong = date.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()

                arrayList.add(Entry(selectedDateToLong.toFloat(), y.toFloat()))

            }
            notifyDataSetChanged(chart,arrayList)
        }

    }

    private fun notifyDataSetChanged(
        chart: LineChart, values: List<Entry?>?
    ) {
        chart.xAxis.valueFormatter = MyXAxisFormatter()
        chart.invalidate()
        setChartData(chart, values!!)
    }

    private fun setChartData(chart: LineChart, values: List<Entry?>) {
        val xAxis = chart.xAxis
        xAxis.setLabelCount(values.count() + 1, true)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        chart.description.isEnabled=false
        xAxis.isEnabled = true
        xAxis.setDrawAxisLine(false)
        xAxis.setDrawGridLines(false)
        val leftAxis = chart.axisLeft
        leftAxis.setDrawLabels(false)
        val lineDataSet: LineDataSet
        if (chart.data != null && chart.data.dataSetCount > 0) {
            lineDataSet = chart.data.getDataSetByIndex(0) as LineDataSet
            lineDataSet.values = values
            chart.data.notifyDataChanged()
            chart.notifyDataSetChanged()
        } else {
            lineDataSet = LineDataSet(values, "")
            lineDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
            lineDataSet.setDrawCircles(false)
            lineDataSet.setDrawValues(false)
            val data = LineData(lineDataSet)
            chart.data = data
            chart.invalidate()
        }
    }

    class MyXAxisFormatter : ValueFormatter() {
        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            val format = SimpleDateFormat("dd LLLL", Locale("ru"))
            return format.format(value.toLong())
        }
    }

    override fun deleteMoodItem(moodItem: MoodItem)
    {
        AlertDialog.Builder(requireActivity())
            .setTitle("Удалить данное состояние?")
            .setPositiveButton("Да") { dialog, which -> moodViewModel.deleteMoodItem(moodItem) }
            .setNegativeButton("Нет") { dialog, which -> dialog.dismiss() }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}