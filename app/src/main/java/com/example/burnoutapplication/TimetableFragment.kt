package com.example.burnoutapplication

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.burnoutapplication.databinding.FragmentTimetableBinding
import com.example.burnoutapplication.list.TodoApplication
import com.example.burnoutapplication.timetable.*
import java.time.DayOfWeek
import java.time.format.DateTimeFormatter
import java.util.*

class TimetableFragment : Fragment() {
    private var _binding: FragmentTimetableBinding?= null
    private val binding get() = _binding!!
    private val timetableViewModel: TimetableViewModel by activityViewModels {
        TimetableViewModel.TimetableViewModelFactory((requireActivity().application as TodoApplication).repository2)
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
        timetableViewModel.timetableItems.observe(activity as LifecycleOwner){
            binding.recyclerView.apply {
                if(isAdded) layoutManager = LinearLayoutManager(requireActivity())
                adapter = TimetableItemAdapter(it.filterList(it,setDay()))
                if (TimetableItemAdapter(it.filterList(it,setDay())).itemCount!=0) binding.tvMessage.visibility = View.GONE
            }
        }
    }

    private fun <E> MutableList<E>.filterList(timetableItems: MutableList<TimetableItem> ,day: DayOfWeek?): MutableList<TimetableItem> {
        val filtered: MutableList<TimetableItem> = ArrayList<TimetableItem>()
        var count = 0
        for (i in timetableItems) {
            if (i.dayOfWeek() == day) {
                filtered.add(i)
                if(isAdded) setAlarm(requireActivity(), count,i)
                count++
            }
        }
        filtered.sortBy{it.startTime()}
        return filtered
    }

    private fun setAlarm(context: Context, alarmId: Int, timetableItem: TimetableItem) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        AlarmReceiver.id= alarmId
        intent.putExtra(titleExtra, timetableItem.name + "    " + DateTimeFormatter.ofPattern("H:mm").format(timetableItem.startTime()) + " - " + DateTimeFormatter.ofPattern("H:mm").format(timetableItem.endTime()))
        val pendingIntent = PendingIntent.getBroadcast(context, alarmId, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)

        val calendar = Calendar.getInstance()
        calendar[Calendar.HOUR_OF_DAY] = timetableItem.startTime()!!.hour
        calendar[Calendar.MINUTE] = timetableItem.startTime()!!.minute
        calendar[Calendar.SECOND] = 0
        if (calendar.time < Date()) calendar.add(Calendar.DAY_OF_MONTH, 7)
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
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