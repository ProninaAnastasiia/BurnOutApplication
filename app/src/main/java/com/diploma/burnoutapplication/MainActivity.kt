package com.diploma.burnoutapplication

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.diploma.burnoutapplication.databinding.ActivityMainBinding

const val MY_SETTINGS = "hasVisited"
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
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

        val sp = getSharedPreferences(
            MY_SETTINGS,
            Context.MODE_PRIVATE
        )
        val hasVisited = sp.getBoolean("hasVisited", false)

        if (!hasVisited) {
            navController.navigate(R.id.action_mainFragment_to_testFragment)
            val e: SharedPreferences.Editor = sp.edit()
            e.putBoolean("hasVisited", true)
            e.apply() // не забудьте подтвердить изменения
        }
    }

}