package com.example.burnoutapplication.list

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.burnoutapplication.databinding.FragmentNewTaskBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class NewTaskFragment(var taskItem: TaskItem?) : DialogFragment() {
    private lateinit var binding: FragmentNewTaskBinding
    //private lateinit var taskViewModel: TaskViewModel     //Изменено, потому что иначе с главного экрана фрагмент не открывался
    private val taskViewModel: TaskViewModel by viewModels {
        TaskItemModelFactory((requireActivity().application as TodoApplication).repository)
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentNewTaskBinding.inflate(inflater,container,false)

        /*val activity = requireActivity()
        taskViewModel = ViewModelProvider(activity)[TaskViewModel::class.java]*/

        if (taskItem != null)
        {
            binding.tvTitle.text = "Редактировать дело"
            binding.deleteButton.visibility = View.VISIBLE
            val editable = Editable.Factory.getInstance()
            binding.name.text = editable.newEditable(taskItem!!.name)
        }
        else
        {
            binding.deleteButton.visibility = View.GONE
            binding.tvTitle.text = "Новое дело"
        }

        binding.saveButton.setOnClickListener {
            saveAction()
        }

        binding.deleteButton.setOnClickListener {
            taskViewModel.deleteTaskItem(taskItem!!)
            dismiss()
        }

        return binding.root
    }

    private fun saveAction()
    {
        val name = binding.name.text.toString()

        if(taskItem == null)
        {
            val newTask = TaskItem(name, DateTimeFormatter.ISO_DATE.format(LocalDate.now()))
            taskViewModel.addTaskItem(newTask)
        }
        else
        {
            taskItem!!.name = name

            taskViewModel.updateTaskItem(taskItem!!)
        }
        dismiss()
    }

}