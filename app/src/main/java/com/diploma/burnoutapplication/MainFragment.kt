package com.diploma.burnoutapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.diploma.burnoutapplication.databinding.FragmentMainBinding
import com.diploma.burnoutapplication.list.NewTaskFragment
import com.diploma.burnoutapplication.mood.NewMoodCardFragment

// TODO: Реализовать появление полезных подсказок (при нажатии на смайлик)
class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding?= null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

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
