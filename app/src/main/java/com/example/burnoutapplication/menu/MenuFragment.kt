package com.example.burnoutapplication.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.navigation.fragment.findNavController
import com.example.burnoutapplication.R
import com.example.burnoutapplication.databinding.FragmentAboutBinding
import com.example.burnoutapplication.databinding.FragmentMenuBinding

// TODO: Доделать меню, кроме теста пока ничего больше нет
class MenuFragment : Fragment() {
    private var _binding: FragmentMenuBinding?= null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        binding.backBtn.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_mainFragment)
        }
        binding.settingsBtn.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_settingsFragment)
        }
        binding.aboutBtn.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_aboutFragment)
        }
        binding.testBtn.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_testFragment)
        }
        binding.tutorialBtn.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_tutorialFragment)
        }
        binding.refBtn.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_refFragment)
        }
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}