package com.diploma.burnoutapplication.timetable

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import java.time.format.DateTimeFormatter
import java.util.*

object AlarmUtils {

    fun setAlarm(context: Context, timetableItem: TimetableItem) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra(titleExtra, timetableItem.name + "    " + DateTimeFormatter.ofPattern("H:mm").format(timetableItem.startTime()) + " - " + DateTimeFormatter.ofPattern("H:mm").format(timetableItem.endTime()))

        val pendingIntent = PendingIntent.getBroadcast(context, getMillis(timetableItem).toInt(), intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            getMillis(timetableItem),
            pendingIntent
        )
    }

    fun cancelAlarm(context: Context, timetableItem: TimetableItem) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, getMillis(timetableItem).toInt(), intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
        alarmManager.cancel(pendingIntent)
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