package com.diploma.burnoutapplication

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupMenu
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.diploma.burnoutapplication.databinding.FragmentListBinding
import com.diploma.burnoutapplication.list.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*

class ListFragment : Fragment(), TaskItemClickListener {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private val taskViewModel: TaskViewModel by activityViewModels {
        TaskItemModelFactory((requireActivity().application as TodoApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)

        binding.newTaskButton.setOnClickListener {
            NewTaskFragment(null).show(parentFragmentManager, "newTaskTag")
        }

        binding.filterButton.setOnClickListener{
            showPopup(binding.filterButton)
        }

        binding.tvMonth.text = "Список выполненных дел"

        setRecyclerView(0)

        return binding.root
    }

    private fun setRecyclerView(filter: Int)
    {
        val fragment = this
        taskViewModel.taskItems.observe(viewLifecycleOwner){
            binding.todoListRecyclerView.apply {
                if(isAdded) layoutManager = LinearLayoutManager(requireActivity())
                adapter = TaskItemAdapter(it.filterList(it as MutableList<TaskItem>,filter), fragment)
                binding.todoListRecyclerView.scrollToPosition(TaskItemAdapter(it.filterList(it as MutableList<TaskItem>,filter), fragment).itemCount - 1)
                if (TaskItemAdapter(it, fragment).itemCount!=0) binding.tvMessage.visibility = View.GONE
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

    private fun showPopup(show: ImageButton) {
        val popup = PopupMenu(requireActivity(), show)
        popup.inflate(R.menu.filter_menu)

        popup.setOnMenuItemClickListener { item: MenuItem? ->
            when (item!!.itemId) {
                R.id.today -> {
                    setRecyclerView(1)
                    binding.tvMonth.text = "Список дел за сегодня"
                }
                R.id.week -> {
                    setRecyclerView(2)
                    binding.tvMonth.text = "Список дел за неделю"
                }
                R.id.month -> {
                    setRecyclerView(3)
                    binding.tvMonth.text = "Список дел за " + SimpleDateFormat("LLLL", Locale("ru")).format(Date())
                }
                R.id.allTime -> {
                    setRecyclerView(0)
                    binding.tvMonth.text = "Список дел за всё время"
                }
            }
            true
        }
        popup.show()
    }

    private fun <E> List<E>.filterList(taskItems: MutableList<TaskItem>, filter: Int): MutableList<TaskItem> {
        val filtered: MutableList<TaskItem> = ArrayList<TaskItem>()

        when(filter){
            1 -> {
                for (i in taskItems) {
                    if (i.completedDateString == DateTimeFormatter.ISO_DATE.format(LocalDate.now())) {
                        filtered.add(i)
                    }
                }
            }
            2 -> {
                for (i in taskItems) {
                    if (ChronoUnit.DAYS.between(i.completedDate(), LocalDate.now())  < 8) {
                        filtered.add(i)
                    }
                }
            }
            3 -> {
                for (i in taskItems) {
                    if (ChronoUnit.MONTHS.between(i.completedDate(), LocalDate.now())  <= 1) {
                        filtered.add(i)
                    }
                }
            }
            else -> {
                return taskItems
            }
        }
        return filtered
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}