package com.example.burnoutapplication.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.navigation.fragment.findNavController
import com.example.burnoutapplication.R

class TutorialFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tutorial, container, false)
        val backBtn = view.findViewById<ImageButton>(R.id.backBtn)
        backBtn.setOnClickListener {
            findNavController().navigate(R.id.action_tutorialFragment_to_menuFragment)
        }

        return view
    }
}