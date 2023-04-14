package com.example.burnoutapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.burnoutapplication.databinding.FragmentListBinding
import com.example.burnoutapplication.list.*

class ListFragment : Fragment(), TaskItemClickListener {
    private lateinit var binding: FragmentListBinding
    private val taskViewModel: TaskViewModel by activityViewModels() {
        TaskItemModelFactory((requireActivity().application as TodoApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(layoutInflater)

        binding.newTaskButton.setOnClickListener {
            NewTaskFragment(null).show(parentFragmentManager, "newTaskTag")
        }

        setRecyclerView()

        return binding.root
    }

    private fun setRecyclerView()
    {
        val fragment = this
        taskViewModel.taskItems.observe(activity as LifecycleOwner){
            binding.todoListRecyclerView.apply {
                layoutManager = LinearLayoutManager(requireActivity())
                adapter = TaskItemAdapter(it, fragment)
            }
        }
    }

    override fun editTaskItem(taskItem: TaskItem)
    {
        NewTaskFragment(taskItem).show(parentFragmentManager,"newTaskTag")
    }

    override fun completeTaskItem(taskItem: TaskItem)
    {
        taskViewModel.setCompleted(taskItem)
    }

    override fun unCompleteTaskItem(taskItem: TaskItem)
    {
        taskViewModel.unCompleted(taskItem)
    }

}