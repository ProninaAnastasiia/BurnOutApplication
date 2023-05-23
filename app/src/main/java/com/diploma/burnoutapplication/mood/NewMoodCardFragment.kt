package com.diploma.burnoutapplication.mood

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.diploma.burnoutapplication.databinding.FragmentNewMoodCardBinding
import com.diploma.burnoutapplication.list.TodoApplication
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

class NewMoodCardFragment : DialogFragment() {
    private lateinit var binding: FragmentNewMoodCardBinding
    private val moodViewModel: MoodViewModel by viewModels {
        MoodItemModelFactory((requireActivity().application as TodoApplication).repositoryMood)
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.WRAP_CONTENT
            dialog.window!!.setLayout(width, height)
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewMoodCardBinding.inflate(layoutInflater)

        binding.saveButton.setOnClickListener {
            saveAction()
        }

        return binding.root
    }

    private fun saveAction() {
        val moodMap = mapOf(
            Mood.NotBad to binding.moodNotBad.isChecked,
            Mood.Tired to binding.moodTired.isChecked,
            Mood.Unbearable to binding.moodUnbearable.isChecked,
            Mood.Guilty to binding.moodGuilty.isChecked,
            Mood.Anxious to binding.moodAnxious.isChecked,
            Mood.Angry to binding.moodAngry.isChecked,
            Mood.Calm to binding.moodCalm.isChecked,
            Mood.Fine to binding.moodFine.isChecked,
        )

        for(i in moodMap){
            if(i.value) {
                val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy").withLocale(Locale("ru"))
                val formatter2 = DateTimeFormatter.ofPattern("HH:mm").withLocale(Locale("ru"))
                val newItem = MoodItem(i.key.rus,i.key.value, LocalDate.now().format(formatter), LocalTime.now().format(formatter2))
                moodViewModel.addMoodItem(newItem)}
        }
        dismiss()
    }

}