package com.diploma.burnoutapplication.menu

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.diploma.burnoutapplication.R
import com.diploma.burnoutapplication.databinding.FragmentMenuBinding

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
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://docs.google.com/forms/d/e/1FAIpQLScpYIDaJW-4lBKQkjXioaxTDGDsOSX_Bnoh-I8TyTE_AEkyCQ/viewform?usp=sf_link"))
            startActivity(browserIntent)
        }



        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}