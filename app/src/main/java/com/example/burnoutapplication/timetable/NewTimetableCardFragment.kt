package com.example.burnoutapplication.timetable

import android.app.TimePickerDialog
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.burnoutapplication.R
import com.example.burnoutapplication.databinding.FragmentNewTimetableCardBinding
import com.example.burnoutapplication.list.TodoApplication
import java.time.DayOfWeek
import java.time.LocalTime

class NewTimetableCardFragment (var timetableItem: TimetableItem?, private val dayOfWeek: DayOfWeek) : DialogFragment() {
    private lateinit var binding: FragmentNewTimetableCardBinding
    private val timetableViewModel: TimetableViewModel by viewModels {
        TimetableViewModel.TimetableViewModelFactory((requireActivity().application as TodoApplication).repository2)
    }
    private var startTime: LocalTime? = LocalTime.of(12,0)
    private var endTime: LocalTime? = LocalTime.of(13,0)

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.WRAP_CONTENT
            dialog.window!!.setLayout(width, height)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = FragmentNewTimetableCardBinding.inflate(inflater,container,false)

        if (timetableItem != null)
        {
            binding.taskTitle.text = "Редактировать расписание"
            val editable = Editable.Factory.getInstance()
            binding.name.text = editable.newEditable(timetableItem!!.name)

            if(timetableItem!!.startTime() != null){
                startTime = timetableItem!!.startTime()!!
                updateTimeButtonText()
            }
            if(timetableItem!!.endTime() != null){
                endTime = timetableItem!!.endTime()!!
                updateTimeButtonText()
            }
        }
        else
        {
            binding.taskTitle.text = "Новое расписание"
        }

        binding.saveButton.setOnClickListener {
            saveAction()
        }

        binding.timeStart.setOnClickListener {
            startTimePicker()
        }

        binding.timeEnd.setOnClickListener {
            endTimePicker()
        }

        return binding.root
    }

    private fun startTimePicker() {
        if(startTime == null)
            startTime = LocalTime.now()
        val listener = TimePickerDialog.OnTimeSetListener{ _, selectedHour, selectedMinute ->
            startTime = LocalTime.of(selectedHour, selectedMinute)
            binding.timeStart.text = String.format("%02d:%02d",startTime!!.hour,startTime!!.minute)
        }
        val dialog = TimePickerDialog(requireActivity(), listener, startTime!!.hour, startTime!!.minute, true)
        dialog.setTitle("Выберите время")
        dialog.show()
    }

    private fun endTimePicker() {
        if(endTime == null)
            endTime = LocalTime.now()
        val listener = TimePickerDialog.OnTimeSetListener{ _, selectedHour, selectedMinute ->
            endTime = LocalTime.of(selectedHour, selectedMinute)
            binding.timeEnd.text = String.format("%02d:%02d",endTime!!.hour,endTime!!.minute)
        }
        val dialog = TimePickerDialog(requireActivity(), listener, endTime!!.hour, endTime!!.minute, true)
        dialog.setTitle("Выберите время")
        dialog.show()
    }

    private fun updateTimeButtonText() {
        binding.timeStart.text = String.format("%02d:%02d",startTime!!.hour,startTime!!.minute)
        binding.timeEnd.text = String.format("%02d:%02d",endTime!!.hour,endTime!!.minute)
    }

    private fun saveAction()
    {
        val name = binding.name.text.toString()

        val startTimeString = if(startTime == null) null else TimetableItem.timeFormatter.format(startTime)
        val endTimeString = if(endTime == null) null else TimetableItem.timeFormatter.format(endTime)
        val dayOfWeekString = TimetableItem.dayFormatter.format(dayOfWeek)

        if(timetableItem == null)
        {
            val newTask = TimetableItem(name, startTimeString, endTimeString, dayOfWeekString)
            timetableViewModel.addTimetableItem(newTask)
        }
        else
        {
            timetableItem!!.name = name
            timetableItem!!.startTimeString = startTimeString
            timetableItem!!.endTimeString = endTimeString
            timetableItem!!.dayOfWeekString = dayOfWeekString
            timetableViewModel.updateTimetableItem(timetableItem!!)
        }
        dismiss()
    }

}