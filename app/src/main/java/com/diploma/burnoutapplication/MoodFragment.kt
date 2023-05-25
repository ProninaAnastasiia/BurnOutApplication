package com.diploma.burnoutapplication

import android.app.AlertDialog
import android.graphics.Canvas
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.diploma.burnoutapplication.databinding.FragmentMoodBinding
import com.diploma.burnoutapplication.database.DatabaseApplication
import com.diploma.burnoutapplication.mood.*
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.renderer.XAxisRenderer
import com.github.mikephil.charting.utils.MPPointF
import com.github.mikephil.charting.utils.Transformer
import com.github.mikephil.charting.utils.Utils
import com.github.mikephil.charting.utils.ViewPortHandler
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

class MoodFragment : Fragment(), MoodItemClickListener {
    private var _binding: FragmentMoodBinding?= null
    private val binding get() = _binding!!
    private lateinit var chart: LineChart

    lateinit var mainHandler: Handler
    private val moodViewModel: MoodViewModel by activityViewModels {
        MoodItemModelFactory((requireActivity().application as DatabaseApplication).repositoryMood)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoodBinding.inflate(inflater, container, false)
        chart = binding.lineChart

        binding.addMoodButton.setOnClickListener{
            NewMoodCardFragment().show(parentFragmentManager, "newMoodTag")
        }

        setChartOptions()

        setRecyclerView()

        mainHandler = Handler(Looper.getMainLooper())

        return binding.root
    }

    private fun setRecyclerView()
    {
        val fragment = this
        moodViewModel.moodItems.observe(viewLifecycleOwner){
            binding.moodRecyclerView.apply {
                if(isAdded) layoutManager = LinearLayoutManager(requireActivity())
                adapter = MoodItemAdapter(it, fragment)
                binding.moodRecyclerView.scrollToPosition(MoodItemAdapter(it,fragment).itemCount - 1)
                if (MoodItemAdapter(it, fragment).itemCount!=0) binding.tvMessage.visibility = View.GONE
                chart.clear()
                addChartDataSet()
            }
        }
    }

    private fun setChartOptions() {
        chart.setDrawGridBackground(false)
        chart.description.isEnabled = false
        chart.isHighlightPerTapEnabled = false
        val xAxis = chart.xAxis
        chart.setScaleEnabled(false)
        chart.description.isEnabled = false
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawAxisLine(false)
        xAxis.setDrawGridLines(false)

        chart.setXAxisRenderer(
            CustomXAxisRenderer(
                chart.viewPortHandler,
                chart.xAxis,
                chart.getTransformer(YAxis.AxisDependency.LEFT)
            )
        )
        chart.axisLeft.apply {
            setDrawLabels(false)
            axisMinimum = 0f
            axisMaximum = 8f
        }

        chart.axisRight.apply {
            setDrawAxisLine(false)
            setDrawGridLines(false)
            setDrawLabels(false)
        }

        chart.legend.form = Legend.LegendForm.NONE

        xAxis.valueFormatter = MyXAxisFormatter()
    }

    private fun addChartDataSet() {
        val data = chart.data
        if (data == null) {
            chart.data = LineData()
        } else {
            val values = ArrayList<Entry>()
            moodViewModel.moodItems.observe(viewLifecycleOwner) {
                val sort1 = it.groupBy({ it.dateAddedString }) { it.mark }

                for(i in sort1) {
                   val y = i.value.sum() / i.value.size.toDouble()
                    val date = LocalDate.parse(
                        i.key,
                        DateTimeFormatter.ofPattern("dd MMMM yyyy").withLocale(Locale("ru"))
                    )
                    val selectedDateToLong = date.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
                    values.add(Entry(selectedDateToLong.toFloat(), y.toFloat()))
                }
            }
            val set = LineDataSet(values, "")
            val color = ContextCompat.getColor(binding.root.context, R.color.purple_500)
            set.mode = LineDataSet.Mode.HORIZONTAL_BEZIER
            set.color = color
            set.setDrawCircles(false)
            set.setDrawValues(false)
            data.addDataSet(set)
            data.notifyDataChanged()
            chart.notifyDataSetChanged()
            chart.setVisibleXRangeMaximum((86400000*7).toFloat())
            chart.invalidate()
            if(values.size<=7){
                chart.xAxis.setLabelCount(values.size, true)
            }
            else { chart.xAxis.setLabelCount(7, true)
            chart.moveViewToX(values.last().x)}
        }

    }

    class MyXAxisFormatter : ValueFormatter() {
        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            val formatDay = SimpleDateFormat("dd")
            val formatMonth = SimpleDateFormat("LLL", Locale("ru"))
            return "${formatDay.format(value.toLong()+86400000)}\n${formatMonth.format(value.toLong())}"
        }
    }

    class CustomXAxisRenderer(
        viewPortHandler: ViewPortHandler?,
        xAxis: XAxis?,
        trans: Transformer?
    ) :
        XAxisRenderer(viewPortHandler, xAxis, trans) {
        override fun drawLabel(
            c: Canvas?,
            formattedLabel: String,
            x: Float,
            y: Float,
            anchor: MPPointF?,
            angleDegrees: Float
        ) {
            val line = formattedLabel.split("\n").toTypedArray()
            Utils.drawXAxisValue(c, line[0], x, y, mAxisLabelPaint, anchor, angleDegrees)
            Utils.drawXAxisValue(c, line[1], x, y + mAxisLabelPaint.textSize, mAxisLabelPaint, anchor, angleDegrees)
        }
    }

    private val updateChart = object : Runnable {
        override fun run() {
            addChartDataSet()
            mainHandler.postDelayed(this, 5000)
        }
    }

    override fun onPause() {
        super.onPause()
        mainHandler.removeCallbacks(updateChart)
    }

    override fun onResume() {
        super.onResume()
        mainHandler.post(updateChart)
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