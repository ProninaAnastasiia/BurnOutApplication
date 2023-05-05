package com.example.burnoutapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import com.example.burnoutapplication.databinding.FragmentMainBinding
import com.example.burnoutapplication.list.NewTaskFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(layoutInflater)

        binding.menuButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_menuFragment)
        }

        binding.addTaskButton.setOnClickListener{
            NewTaskFragment(null).show(childFragmentManager, "newTaskTag")
        }

        return binding.root
    }
}
