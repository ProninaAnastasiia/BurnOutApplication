package com.diploma.burnoutapplication.list

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.diploma.burnoutapplication.databinding.FragmentNewTaskBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class NewTaskFragment(var taskItem: TaskItem?) : DialogFragment() {
    private var _binding: FragmentNewTaskBinding? = null
    private val binding get() = _binding!!
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        _binding = FragmentNewTaskBinding.inflate(inflater,container,false)

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}