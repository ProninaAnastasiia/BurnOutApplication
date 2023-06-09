package com.diploma.burnoutapplication.timetable

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupMenu
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.diploma.burnoutapplication.R
import com.diploma.burnoutapplication.databinding.FragmentEditPageBinding
import java.time.DayOfWeek
import java.util.ArrayList

private const val NUM = "num"

class EditPageFragment : Fragment(), TimetableCardClickListener {
    private var _binding: FragmentEditPageBinding?= null
    private val binding get() = _binding!!
    private lateinit var timetableViewModel: TimetableViewModel
    private var pageNumber:Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageNumber = if (arguments != null) requireArguments().getInt("num") else 1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditPageBinding.inflate(inflater, container, false)

        val activity = requireActivity()
        timetableViewModel = ViewModelProvider(activity)[TimetableViewModel::class.java]

        setRecyclerView()

        binding.addButton.setOnClickListener{
            NewTimetableCardFragment(null, whatDay()).show(parentFragmentManager, "newTaskTag")
        }

        return binding.root
    }

    private fun setRecyclerView()
    {
        val fragment = this
        timetableViewModel.timetableItems.observe(viewLifecycleOwner){
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
                    SelectionForCopyFragment(timetableItem,null).show(
                        parentFragmentManager,
                        "newCopyFragmentTag"
                    )
                }
                R.id.delete -> {
                    timetableViewModel.deleteTimetableItem(timetableItem)
                    AlarmUtils().cancelAlarm(binding.root.context,timetableItem)
                }
            }

            true
        }

        popup.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}