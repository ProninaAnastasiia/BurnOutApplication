package com.diploma.burnoutapplication.mood

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.navigation.NavDeepLinkBuilder
import com.diploma.burnoutapplication.R
import com.diploma.burnoutapplication.timetable.AlarmUtils
import java.util.*

class MoodReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val pendingIntent = NavDeepLinkBuilder(context)
            .setGraph(R.navigation.main_nav)
            .setDestination(R.id.moodFragment)
            .createPendingIntent()

        val notification = NotificationCompat.Builder(context, "moodChannel")
            .setSmallIcon(R.drawable.ic_mood_24)
            .setContentTitle("Контроль настроения")
            .setContentText("Отметьте свое общее эмоциональное состояние за весь день")
            .setContentIntent(pendingIntent)
            .build()

        val  manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(0, notification)

        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, 1)
        AlarmUtils().initRepeatingAlarm(calendar)

    }
}