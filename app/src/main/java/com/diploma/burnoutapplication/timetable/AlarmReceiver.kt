package com.diploma.burnoutapplication.timetable

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.navigation.NavDeepLinkBuilder
import com.diploma.burnoutapplication.R
import java.util.*


const val channelID = "channel1"
const val titleExtra = "titleExtra"
class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent)
    {
        val pendingIntent = NavDeepLinkBuilder(context)
            .setGraph(R.navigation.main_nav)
            .setDestination(R.id.timetableFragment)
            .createPendingIntent()

        val notification = NotificationCompat.Builder(context, channelID)
            .setSmallIcon(R.drawable.ic_timetable)
            .setContentTitle(intent.getStringExtra(titleExtra))
            .setContentText("Пора перейти к следующему пункту расписания")
            .setContentIntent(pendingIntent)
            .build()

        val  manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(id, notification)

        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, 1)
        AlarmUtils().initRepeatingAlarm(calendar)

    }

    companion object {
        var id: Int = 0
    }
}