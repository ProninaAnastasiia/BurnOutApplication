package com.example.burnoutapplication.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.navigation.fragment.findNavController
import com.example.burnoutapplication.R
import com.example.burnoutapplication.databinding.FragmentAboutBinding
import com.example.burnoutapplication.databinding.FragmentTestBinding

class AboutFragment : Fragment() {
    private var _binding: FragmentAboutBinding?= null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)

        binding.backBtn.setOnClickListener {
            findNavController().navigate(R.id.action_aboutFragment_to_menuFragment)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}