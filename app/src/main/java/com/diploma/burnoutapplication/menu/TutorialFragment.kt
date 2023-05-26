package com.diploma.burnoutapplication.menu

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.diploma.burnoutapplication.R
import com.diploma.burnoutapplication.databinding.FragmentTutorialBinding
import com.google.android.material.tabs.TabLayoutMediator


class TutorialFragment : Fragment() {
    private var _binding: FragmentTutorialBinding?= null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTutorialBinding.inflate(inflater, container, false)

        binding.viewPager.adapter = TutorialPagerAdapter(requireActivity())

        TabLayoutMediator(binding.tabLayout, binding.viewPager){
                tab, pos ->
        }.attach()

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                when(position){
                    3 -> {
                        binding.skipButton.text = "Пройти тест"
                        binding.skipButton.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.purple_200))

                    }
                    else -> {
                        binding.skipButton.text = "Пропустить обучение"
                        binding.skipButton.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.purple_100))
                    }
                }
            }
        })

        binding.skipButton.setOnClickListener {
            findNavController().navigate(R.id.action_tutorialFragment_to_testFragment)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}