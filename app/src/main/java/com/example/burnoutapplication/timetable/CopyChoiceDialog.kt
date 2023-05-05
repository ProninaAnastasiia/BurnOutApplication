package com.example.burnoutapplication.timetable

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.burnoutapplication.R
import com.example.burnoutapplication.list.TodoApplication

class CopyChoiceDialog (private val _context: Context, private val dayOfWeek: String) : DialogFragment() {
    private  var builder: AlertDialog.Builder? = null
    private val timetableViewModel: TimetableViewModel by viewModels {
        TimetableViewModel.TimetableViewModelFactory((requireActivity().application as TodoApplication).repository2)
    }
    private  var pos: Int = 0

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        builder = AlertDialog.Builder(_context)
        val data: Array<String> = requireActivity().resources.getStringArray(R.array.days)

        builder!!.setTitle("Выберите дни недели").setSingleChoiceItems(data,pos) { dialogInterface, j ->
            pos = j
        }
        builder!!.setPositiveButton("Ок"){ dialogInterface, j ->
            timetableViewModel.copyTimetable(dayOfWeek, whatDay(pos))
            dialogInterface.dismiss()
        }
        builder!!.setNegativeButton("Отмена"){ dialogInterface, j ->
            dialogInterface.dismiss()
        }
        return builder!!.create()
    }

    private fun whatDay(i:Int): String {
        return when(i){
            0 -> "Monday"
            1 -> "Tuesday"
            2 -> "Wednesday"
            3 -> "Thursday"
            4 -> "Friday"
            5 -> "Saturday"
            else -> "Sunday"
        }
    }

}