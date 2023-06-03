package com.diploma.burnoutapplication.timetable

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import com.diploma.burnoutapplication.mood.MoodReceiver
import java.time.format.DateTimeFormatter
import java.util.*

class AlarmUtils() {
    private var alarmMgr: AlarmManager? = null
    private lateinit var alarmIntent: PendingIntent

    fun initRepeatingAlarm(calendar: Calendar){
        alarmMgr?.set(  AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            alarmIntent)
    }

    fun setAlarm(context: Context, timetableItem: TimetableItem) {
        alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmIntent = Intent(context, AlarmReceiver::class.java).let { mIntent ->
            mIntent.putExtra(titleExtra, timetableItem.name + "    " + DateTimeFormatter
                .ofPattern("H:mm").format(timetableItem.startTime()) + " - " +
                    DateTimeFormatter.ofPattern("H:mm").format(timetableItem.endTime()))
            PendingIntent.getBroadcast(context, getMillis(timetableItem).toInt(), mIntent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
        }
        alarmMgr?.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            getMillis(timetableItem),
            alarmIntent
        )
    }

    fun cancelAlarm(context: Context, timetableItem: TimetableItem) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, getMillis(timetableItem).toInt(),
            intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
        alarmManager.cancel(pendingIntent)
    }

    fun setNotificationForMood(context: Context){
        alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        alarmIntent = Intent(context, MoodReceiver::class.java).let { mIntent ->
            PendingIntent.getBroadcast(context, 0, mIntent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
        }

        val calendar = Calendar.getInstance()
        calendar[Calendar.HOUR_OF_DAY] = 6
        calendar[Calendar.MINUTE] = 22
        calendar[Calendar.SECOND] = 0
        calendar[Calendar.MILLISECOND] = 0
        if (calendar.time < Date()) calendar.add(Calendar.DAY_OF_MONTH, 1)

        alarmMgr?.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            alarmIntent
        )
    }

    private fun getMillis(timetableItem: TimetableItem): Long{
        val calendar = Calendar.getInstance()
        calendar[Calendar.DAY_OF_WEEK] = timetableItem.dayOfWeek()!!.value+1
        calendar[Calendar.HOUR_OF_DAY] = timetableItem.startTime()!!.hour
        calendar[Calendar.MINUTE] = timetableItem.startTime()!!.minute
        calendar[Calendar.SECOND] = 0
        calendar[Calendar.MILLISECOND] = 0
        if (calendar.time < Date()) calendar.add(Calendar.DAY_OF_MONTH, 7)
        return calendar.timeInMillis
    }

}