package com.diploma.burnoutapplication.timetable

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupMenu
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.diploma.burnoutapplication.R
import com.diploma.burnoutapplication.databinding.FragmentEditTimetableBinding
import com.google.android.material.tabs.TabLayoutMediator
import java.util.*


class EditTimetableFragment : Fragment() {
    private var _binding: FragmentEditTimetableBinding?= null
    private val binding get() = _binding!!
    private lateinit var timetableViewModel: TimetableViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditTimetableBinding.inflate(layoutInflater)

        val activity = requireActivity()
        timetableViewModel = ViewModelProvider(activity)[TimetableViewModel::class.java]

        binding.viewPager.adapter = EditPagerAdapter(requireActivity())
        TabLayoutMediator(binding.tabLayout, binding.viewPager){
                tab, pos ->
        }.attach()

        binding.backBtn.setOnClickListener{
            findNavController().navigate(R.id.action_editTimetableFragment_to_timetableFragment)
        }

        binding.moreBtn.setOnClickListener{
            showPopup(binding.moreBtn)
        }

        setDay()

        return binding.root
    }

    private fun showPopup(show: ImageButton) {
        val popup = PopupMenu(requireActivity(), show)
        popup.inflate(R.menu.popup_menu)
        popup.menu.findItem(R.id.delete).title = "Очистить день"

        popup.setOnMenuItemClickListener { item: MenuItem? ->
            when (item!!.itemId) {
                R.id.copy -> {
                    SelectionForCopyFragment(null, whatDay()).show(
                        parentFragmentManager,
                        "newCopyFragmentTag"
                    )
                }
                R.id.delete -> {
                    timetableViewModel.deleteTimetable(whatDay())
                }
            }
            true
        }
        popup.show()
    }

    private fun setDay() {
        val sdf = SimpleDateFormat("EEEE")
        val d = Date()
        when(sdf.format(d)){
            "Monday" -> binding.viewPager.setCurrentItem(0,false)
            "Tuesday" -> binding.viewPager.setCurrentItem(1,false)
            "Wednesday" -> binding.viewPager.setCurrentItem(2,false)
            "Thursday" -> binding.viewPager.setCurrentItem(3,false)
            "Friday" -> binding.viewPager.setCurrentItem(4,false)
            "Saturday" -> binding.viewPager.setCurrentItem(5,false)
            else -> binding.viewPager.setCurrentItem(6,false)
        }
    }

    private fun whatDay(): String {
        return when(binding.viewPager.currentItem){
            0 -> "Monday"
            1 -> "Tuesday"
            2 -> "Wednesday"
            3 -> "Thursday"
            4 -> "Friday"
            5 -> "Saturday"
            else -> "Sunday"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}