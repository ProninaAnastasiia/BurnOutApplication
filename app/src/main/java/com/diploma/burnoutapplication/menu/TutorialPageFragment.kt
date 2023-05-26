package com.diploma.burnoutapplication.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.diploma.burnoutapplication.R
import com.diploma.burnoutapplication.databinding.FragmentTutorialPageBinding


private const val PAGE = "num"
class TutorialPageFragment : Fragment() {
    private var _binding: FragmentTutorialPageBinding?= null
    private val binding get() = _binding!!
    private var pageNumber:Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageNumber = if (arguments != null) requireArguments().getInt("num") else 1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTutorialPageBinding.inflate(inflater, container, false)

        whatPage()

        return binding.root
    }

    private fun whatPage() {
        when(pageNumber){
            0 -> {
                binding.tutorialImage.setImageResource(R.drawable.mood_tutorial)
                binding.tutorialTitle.text = "Отмечайте и наблюдайте"
                binding.tutorialTextView.text = "Фиксируйте настроение в течение дня, чтобы следить за статистикой эмоционального состояния."
            }
            1 -> {
                binding.tutorialImage.setImageResource(R.drawable.timetable_tutorial)
                binding.tutorialTitle.text = "Здоровый режим и отдых"
                binding.tutorialTextView.text = "Создайте своё расписание дня с обязательными часами отдыха, чтобы ощутить контроль над своим временем."
            }
            2 -> {
                binding.tutorialImage.setImageResource(R.drawable.list_tutorial)
                binding.tutorialTitle.text = "Авторизация успеха"
                binding.tutorialTextView.text = "Убедитесь, как простое записывание выполненных дел уменьшает тревогу и помогает осознать Ваши старания."
            }
            else -> {
                binding.tutorialImage.setImageResource(R.drawable.test_tutorial)
                binding.tutorialTitle.text = "Проверьте себя"
                binding.tutorialTextView.text = "Пройдите тест на эмоциональное выгорание К. Маслач, чтобы развеять свои сомнения о собственном эмоциональном здоровье."
            }
        }
    }

    companion object {

        fun newInstance(page: Int) = TutorialPageFragment().apply {
            arguments = Bundle(1).apply {
                putInt(PAGE, page)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}