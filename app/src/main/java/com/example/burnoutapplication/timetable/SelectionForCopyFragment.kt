package com.example.burnoutapplication.timetable

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.burnoutapplication.R
import com.example.burnoutapplication.databinding.FragmentSelectionForCopyBinding
import com.example.burnoutapplication.list.TodoApplication

class SelectionForCopyFragment (var timetableItem: TimetableItem) : DialogFragment() {
    private lateinit var binding: FragmentSelectionForCopyBinding
    private val timetableViewModel: TimetableViewModel by viewModels {
        TimetableViewModel.TimetableViewModelFactory((requireActivity().application as TodoApplication).repository2)
    }

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

        binding = FragmentSelectionForCopyBinding.inflate(inflater,container,false)

        binding.saveButton.setOnClickListener {
            saveAction()
        }

        return binding.root
    }

    private fun saveAction() {
        val daysMap = mapOf(
            "Monday" to binding.chMon.isChecked, "Tuesday" to binding.chTue.isChecked,
            "Wednesday" to binding.chWen.isChecked, "Thursday" to binding.chThu.isChecked,
            "Friday" to binding.chFri.isChecked, "Saturday" to binding.chSat.isChecked,
            "Sunday" to binding.chSun.isChecked,)

        for(i in daysMap){
            if(i.value) {
                timetableViewModel.copyTimetableItem(timetableItem, i.key)}
        }
        dismiss()
    }
}