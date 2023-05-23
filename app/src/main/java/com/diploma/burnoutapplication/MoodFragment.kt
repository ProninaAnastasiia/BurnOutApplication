package com.diploma.burnoutapplication

import android.app.AlertDialog
import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.diploma.burnoutapplication.databinding.FragmentMoodBinding
import com.diploma.burnoutapplication.list.TodoApplication
import com.diploma.burnoutapplication.mood.*
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


// TODO: Добавить прокрутку moodRecyclerView до последнего элемента и  как в расписании и списке дел придумать, что отобразить пока данных нет
class MoodFragment : Fragment(), MoodItemClickListener {
    private var _binding: FragmentMoodBinding?= null
    private val binding get() = _binding!!
    lateinit var arrayList: ArrayList<Entry>
    private val moodViewModel: MoodViewModel by activityViewModels {
        MoodItemModelFactory((requireActivity().application as TodoApplication).repositoryMood)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoodBinding.inflate(inflater, container, false)

        binding.addMoodButton.setOnClickListener{
            NewMoodCardFragment().show(parentFragmentManager, "newMoodTag")
        }

        setData()
        setRecyclerView()

        return binding.root
    }

    private fun setRecyclerView()
    {
        val fragment = this
        moodViewModel.moodItems.observe(viewLifecycleOwner){
            binding.moodRecyclerView.apply {
                if(isAdded) layoutManager = LinearLayoutManager(requireActivity())
                adapter = MoodItemAdapter(it, fragment)
            }
        }
    }

    private fun setData()
    {
        arrayList = ArrayList()

        val chart = binding.lineChart

            moodViewModel.moodItems.observe(viewLifecycleOwner){
                val sort1 = it.groupBy({it.dateAddedString}) {it.mark}

                for(i in sort1){
                    val y = i.value.sum()/i.value.size.toDouble()
                    val date = LocalDate.parse(i.key, DateTimeFormatter.ofPattern("dd MMMM yyyy").withLocale(Locale("ru")))
                    val selectedDateToLong = date.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
                    arrayList.add(Entry(selectedDateToLong.toFloat(), y.toFloat()))
                    val lineDataSet = LineDataSet(arrayList, "")
                    val data = LineData(lineDataSet)
                    chart.data = data
                    chart.data.notifyDataChanged()
                    chart.notifyDataSetChanged()

                    chart.invalidate()
                    arrayList.last()?.let { chart.moveViewToX(it.x) }
                    //chart.moveViewToX(arrayList.last().x)

                    val xAxis = chart.xAxis
                    chart.setScaleEnabled(false)
                    chart.description.isEnabled = false
                    xAxis.position = XAxis.XAxisPosition.BOTTOM
                    xAxis.setDrawAxisLine(false)
                    xAxis.setDrawGridLines(false)

                    xAxis.setLabelCount(7, true)

                    chart.setXAxisRenderer(
                        CustomXAxisRenderer(
                            chart.viewPortHandler,
                            chart.xAxis,
                            chart.getTransformer(YAxis.AxisDependency.LEFT)
                        )
                    )
                    chart.axisLeft.setDrawLabels(false)
                    chart.axisLeft.axisMinimum = 0f
                    chart.axisLeft.axisMaximum = 8f

                    chart.axisRight.setDrawAxisLine(false)
                    chart.axisRight.setDrawGridLines(false)
                    chart.axisRight.setDrawLabels(false)

                    chart.legend.form = Legend.LegendForm.NONE
                    chart.setVisibleXRangeMaximum((86507520*6).toFloat()) // Сколько точек отображается на интерфейсе (другие точки можно увидеть, сдвинув график)

                    val color = ContextCompat.getColor(binding.root.context, R.color.purple_500)

                    lineDataSet.mode = LineDataSet.Mode.HORIZONTAL_BEZIER
                    lineDataSet.color = color
                    lineDataSet.setDrawCircles(false)
                    lineDataSet.setDrawValues(false)

                    xAxis.valueFormatter = MyXAxisFormatter()
                }
            }

    }

    class MyXAxisFormatter : ValueFormatter() {
        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            val formatDay = SimpleDateFormat("dd")
            val formatMonth = SimpleDateFormat("LLL", Locale("ru"))
            return "${formatDay.format(value.toLong())}\n${formatMonth.format(value.toLong())}"
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

    override fun deleteMoodItem(moodItem: MoodItem)
    {
        AlertDialog.Builder(requireActivity())
            .setTitle("Удалить данное состояние?")
            .setPositiveButton("Да") { dialog, which ->
                val x = moodItem.dateAddedString
                val date = LocalDate.parse(x, DateTimeFormatter.ofPattern("dd MMMM yyyy").withLocale(Locale("ru")))
                val selectedDateToLong = date.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
                binding.lineChart.data.removeEntry(selectedDateToLong.toFloat(),0)
                binding.lineChart.data.notifyDataChanged()
                binding.lineChart.notifyDataSetChanged()
                moodViewModel.deleteMoodItem(moodItem) }
            .setNegativeButton("Нет") { dialog, which -> dialog.dismiss() }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}