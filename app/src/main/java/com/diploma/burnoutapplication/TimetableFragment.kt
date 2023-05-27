package com.diploma.burnoutapplication

import android.app.NotificationChannel
import android.app.NotificationManager
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.diploma.burnoutapplication.databinding.FragmentTimetableBinding
import com.diploma.burnoutapplication.database.DatabaseApplication
import com.diploma.burnoutapplication.timetable.*
import java.time.DayOfWeek
import java.util.*

class TimetableFragment : Fragment() {
    private var _binding: FragmentTimetableBinding?= null
    private val binding get() = _binding!!
    private val timetableViewModel: TimetableViewModel by activityViewModels {
        TimetableViewModel.TimetableViewModelFactory((requireActivity().application as DatabaseApplication).repositoryTimetable)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTimetableBinding.inflate(layoutInflater)

        createNotificationChannel()

        binding.editTimetableBtn.setOnClickListener{
            findNavController().navigate(R.id.action_timetableFragment_to_editTimetableFragment)
        }

        setDay()

        setRecyclerView()

        return binding.root
    }

    private fun setRecyclerView()
    {
        timetableViewModel.timetableItemsByDay.observe(viewLifecycleOwner){
            binding.recyclerView.apply {
                if(isAdded) layoutManager = LinearLayoutManager(requireActivity())
                adapter = TimetableItemAdapter(it)
                if (it.size!=0) binding.tvMessage.visibility = View.GONE
            }
        }
    }

    private fun createNotificationChannel(){
        val name: CharSequence = "reminderChannel"
        val desc = "Channel fo Alarm Manager"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelID,name,importance)
        channel.description = desc
        val notificationManager =
            ContextCompat.getSystemService(requireActivity(), NotificationManager::class.java)
        notificationManager!!.createNotificationChannel(channel)
    }

    private fun setDay(): DayOfWeek {
        val dayOfWeek: DayOfWeek?
        val sdf = SimpleDateFormat("EEEE")
        val d = Date()

        when(sdf.format(d)){
            "Monday" -> {
                binding.tvDay.text = "Понедельник"
                dayOfWeek = DayOfWeek.MONDAY
            }
            "Tuesday" -> {
                binding.tvDay.text = "Вторник"
                dayOfWeek = DayOfWeek.TUESDAY
            }
            "Wednesday" -> {
                binding.tvDay.text = "Среда"
                dayOfWeek = DayOfWeek.WEDNESDAY
            }
            "Thursday" -> {
                binding.tvDay.text = "Четверг"
                dayOfWeek = DayOfWeek.THURSDAY
            }
            "Friday" -> {
                binding.tvDay.text = "Пятница"
                dayOfWeek = DayOfWeek.FRIDAY
            }
            "Saturday" -> {
                binding.tvDay.text = "Суббота"
                dayOfWeek = DayOfWeek.SATURDAY
            }
            else -> {
                binding.tvDay.text = "Воскресенье"
                dayOfWeek = DayOfWeek.SUNDAY
            }
        }
        return dayOfWeek
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}