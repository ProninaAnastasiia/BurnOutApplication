package com.diploma.burnoutapplication

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.diploma.burnoutapplication.databinding.ActivityMainBinding
import com.diploma.burnoutapplication.mood.MoodReceiver
import com.diploma.burnoutapplication.timetable.AlarmUtils
import java.util.*


const val MY_SETTINGS = "hasVisited"
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.
        findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.testFragment -> binding.bottomNavigationView.visibility = View.GONE
                R.id.resultFragment -> binding.bottomNavigationView.visibility = View.GONE
                else -> {
                    binding.bottomNavigationView.visibility = View.VISIBLE
                }
            }
        }

        createNotificationChannel()

        val sp = getSharedPreferences(
            MY_SETTINGS,
            Context.MODE_PRIVATE
        )
        val hasVisited = sp.getBoolean("hasVisited", false)

        if (!hasVisited) {
            navController.navigate(R.id.action_mainFragment_to_tutorialFragment)
            AlarmUtils().setNotificationForMood(this)
            val e: SharedPreferences.Editor = sp.edit()
            e.putBoolean("hasVisited", true)
            e.apply()
        }
    }

    private fun createNotificationChannel(){
        val name: CharSequence = "Напоминание отметить настроение"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("moodChannel",name,importance)
        val notificationManager =
            ContextCompat.getSystemService(this, NotificationManager::class.java)
        notificationManager!!.createNotificationChannel(channel)
    }

}