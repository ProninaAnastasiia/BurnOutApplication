package com.diploma.burnoutapplication.timetable

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.diploma.burnoutapplication.databinding.FragmentSelectionForCopyBinding

class SelectionForCopyFragment (var timetableItem: TimetableItem?, private val dayOfWeek: String?) : DialogFragment() {
    private var _binding: FragmentSelectionForCopyBinding?= null
    private val binding get() = _binding!!
    private lateinit var timetableViewModel:TimetableViewModel

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

        _binding = FragmentSelectionForCopyBinding.inflate(inflater,container,false)

        val activity = requireActivity()
        timetableViewModel = ViewModelProvider(activity)[TimetableViewModel::class.java]

        if (timetableItem != null)
        {
            binding.saveButton.setOnClickListener {
                copyOneItem()
            }
        }
        else
        {
            binding.saveButton.setOnClickListener {
                copyManyItems()
            }
        }

        return binding.root
    }

    private fun copyManyItems() {
        val daysMap = mapOf(
            "Monday" to binding.chMon.isChecked, "Tuesday" to binding.chTue.isChecked,
            "Wednesday" to binding.chWen.isChecked, "Thursday" to binding.chThu.isChecked,
            "Friday" to binding.chFri.isChecked, "Saturday" to binding.chSat.isChecked,
            "Sunday" to binding.chSun.isChecked,)

        for(i in daysMap){
            if(i.value) {
                timetableViewModel.copyTimetable(dayOfWeek!!, i.key)}
        }
        dismiss()
    }

    private fun copyOneItem() {
        val daysMap = mapOf(
            "Monday" to binding.chMon.isChecked, "Tuesday" to binding.chTue.isChecked,
            "Wednesday" to binding.chWen.isChecked, "Thursday" to binding.chThu.isChecked,
            "Friday" to binding.chFri.isChecked, "Saturday" to binding.chSat.isChecked,
            "Sunday" to binding.chSun.isChecked,)

        for(i in daysMap){
            if(i.value) {
                timetableViewModel.copyTimetableItem(timetableItem!!, i.key)}
        }
        dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}