package com.example.burnoutapplication.timetable

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.burnoutapplication.R

const val channelID = "channel1"
const val titleExtra = "titleExtra"

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent)
    {
        val notification = NotificationCompat.Builder(context, channelID)
            .setSmallIcon(R.drawable.ic_timetable)
            .setContentTitle(intent.getStringExtra(titleExtra))
            .setContentText("Пора перейти к следующему пункту расписания")
            .build()

        val  manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(id, notification)
    }

    companion object {
        var id: Int = 0
    }
}