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
import com.example.burnoutapplication.mood.NewMoodCardFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
// TODO: Реализовать появление полезных подсказок (при нажатии на смайлик)
class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding?= null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(layoutInflater)

        binding.menuButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_menuFragment)
        }

        binding.addTaskButton.setOnClickListener{
            NewTaskFragment(null).show(parentFragmentManager, "newTaskTag")
        }

        binding.addMoodButton.setOnClickListener{
            NewMoodCardFragment().show(parentFragmentManager, "newMoodTag")
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
