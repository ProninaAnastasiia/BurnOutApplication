package com.example.burnoutapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        bottomNavigationView.setupWithNavController(navController)


        navController.addOnDestinationChangedListener { _, destination, _ ->
           // TransitionManager.beginDelayedTransition(bottomNavigationView, Fade())  bottomNavigation пропадает не мгновенно
            when (destination.id) {
                R.id.moodFragment -> bottomNavigationView.visibility = View.VISIBLE
                R.id.mainFragment -> bottomNavigationView.visibility = View.VISIBLE
                R.id.timetableFragment -> bottomNavigationView.visibility = View.VISIBLE
                R.id.listFragment -> bottomNavigationView.visibility = View.VISIBLE
                else -> {
                    bottomNavigationView.visibility = View.GONE
                }
            }

        }
    }

}