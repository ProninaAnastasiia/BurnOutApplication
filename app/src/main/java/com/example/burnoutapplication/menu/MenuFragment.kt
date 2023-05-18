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
// TODO: Доделать меню, кроме теста пока ничего больше нет
class MenuFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_menu, container, false)
        val backBtn = view.findViewById<ImageButton>(R.id.backBtn)
        backBtn.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_mainFragment)
        }
        val settingsBtn = view.findViewById<Button>(R.id.settingsBtn)
        settingsBtn.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_settingsFragment)
        }
        val aboutBtn = view.findViewById<Button>(R.id.aboutBtn)
        aboutBtn.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_aboutFragment)
        }
        val testBtn = view.findViewById<Button>(R.id.testBtn)
        testBtn.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_testFragment)
        }
        val tutorialBtn = view.findViewById<Button>(R.id.tutorialBtn)
        tutorialBtn.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_tutorialFragment)
        }
        val refBtn = view.findViewById<Button>(R.id.refBtn)
        refBtn.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_refFragment)
        }
        return view
    }
}