package com.example.burnoutapplication.timetable

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupMenu
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.burnoutapplication.R
import com.example.burnoutapplication.databinding.FragmentEditPageBinding
import com.example.burnoutapplication.list.TodoApplication
import java.time.DayOfWeek
import java.util.ArrayList

private const val NUM = "num"

class EditPageFragment : Fragment(), TimetableCardClickListener {

    private lateinit var binding: FragmentEditPageBinding
    private val timetableViewModel: TimetableViewModel by viewModels {
        TimetableViewModel.TimetableViewModelFactory((requireActivity().application as TodoApplication).repository2)
    }
    private var pageNumber:Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageNumber = if (arguments != null) requireArguments().getInt("num") else 1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditPageBinding.inflate(layoutInflater)

        setRecyclerView()

        binding.addButton.setOnClickListener{
            NewTimetableCardFragment(null, whatDay()).show(parentFragmentManager, "newTaskTag")
        }

        return binding.root
    }

    private fun setRecyclerView()
    {
        val fragment = this
        timetableViewModel.timetableItems.observe(activity as LifecycleOwner){
            binding.recyclerView.apply {
                if(isAdded) layoutManager = LinearLayoutManager(requireActivity())
                adapter = TimetableItemEditAdapter(it.filterList(it,whatDay()), fragment)
            }
        }
    }

    private fun <E> MutableList<E>.filterList(timetableItems: MutableList<TimetableItem> ,day: DayOfWeek?): MutableList<TimetableItem> {
        val filtered: MutableList<TimetableItem> = ArrayList<TimetableItem>()
        for (i in timetableItems) {
            if (i.dayOfWeek() == day) {
                filtered.add(i)
            }
        }
        filtered.sortBy{it.startTime()}
        return filtered
    }

    private fun whatDay(): DayOfWeek {
        val dayOfWeek: DayOfWeek
        when(pageNumber){
            0 -> {
                dayOfWeek = DayOfWeek.MONDAY
                binding.tvDay.text = "Понедельник"
            }
            1 -> {
                dayOfWeek = DayOfWeek.TUESDAY
                binding.tvDay.text = "Вторник"
            }
            2 -> {
                dayOfWeek = DayOfWeek.WEDNESDAY
                binding.tvDay.text = "Среда"
            }
            3 -> {
                dayOfWeek = DayOfWeek.THURSDAY
                binding.tvDay.text = "Четверг"
            }
            4 -> {
                dayOfWeek = DayOfWeek.FRIDAY
                binding.tvDay.text = "Пятница"
            }
            5 -> {
                dayOfWeek = DayOfWeek.SATURDAY
                binding.tvDay.text = "Суббота"
            }
            else -> {
                dayOfWeek = DayOfWeek.SUNDAY
                binding.tvDay.text = "Воскресенье"
            }
        }
        return dayOfWeek
    }

    companion object {

        fun newInstance(page: Int) = EditPageFragment().apply {
            arguments = Bundle(1).apply {
                putInt(NUM, page)
            }
        }
    }

    override fun editTimetableItem(timetableItem: TimetableItem) {
        NewTimetableCardFragment(timetableItem, timetableItem.dayOfWeek()!!).show(parentFragmentManager,"newTaskTag")
    }

    override fun showPopup(show: ImageButton, timetableItem: TimetableItem) {
        val popup = PopupMenu(requireActivity(), show)
        popup.inflate(R.menu.popup_menu)

        popup.setOnMenuItemClickListener { item: MenuItem? ->

            when (item!!.itemId) {
                R.id.copy -> {
                    SelectionForCopyFragment(timetableItem).show(
                        parentFragmentManager,
                        "newCopyFragmentTag"
                    )
                }
                R.id.delete -> {
                    timetableViewModel.deleteTimetableItem(timetableItem)
                }
            }

            true
        }

        popup.show()
    }
}