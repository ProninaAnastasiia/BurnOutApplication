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
import com.diploma.burnoutapplication.test.Question
import org.json.JSONArray
import org.json.JSONObject
import kotlin.random.Random

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding?= null
    private val binding get() = _binding!!
    private var affirmationsList : MutableList<String> = ArrayList()
    private  var count = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        affirmationsList = getAffirmations()

        binding.menuButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_menuFragment)
        }

        binding.addTaskButton.setOnClickListener{
            NewTaskFragment(null).show(parentFragmentManager, "newTaskTag")
        }

        binding.addMoodButton.setOnClickListener{
            NewMoodCardFragment().show(parentFragmentManager, "newMoodTag")
        }

        binding.affirmationsButton.setOnClickListener{
            binding.textView.text = setAffirmation()
            count++
        }

        return binding.root
    }

    private fun getAffirmations(): MutableList<String>  {
        val list : MutableList<String> = ArrayList()
        val jsonData = resources.openRawResource(R.raw.affirmations).bufferedReader().use{it.readText()}

        val jsonStr = JSONObject(jsonData)

        val questions = jsonStr.getJSONArray("affirmations") as JSONArray

        for(i in 0 until questions.length()){
            val text = questions.getJSONObject(i).getString("text")
            list.add(text)
        }

        return list
    }

    private fun setAffirmation(): String {
        if(count>=affirmationsList.size) {
            affirmationsList.shuffle()
            count = 0
        }
        return affirmationsList[count]
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
