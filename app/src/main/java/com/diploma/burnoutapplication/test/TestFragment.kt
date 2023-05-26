package com.diploma.burnoutapplication.test

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.diploma.burnoutapplication.R
import com.diploma.burnoutapplication.databinding.FragmentTestBinding
import org.json.JSONArray
import org.json.JSONObject
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sqrt

class TestFragment : Fragment() {
    private var _binding: FragmentTestBinding?= null
    private val binding get() = _binding!!
    private var mCurrentPosition: Int = 1
    private var mQuestionsList: ArrayList<Question>? = null
    private var answers = ArrayList<String>()
    private var answersNum = ArrayList<Int>()
    private var emotionalExhaustion: Int = 0
    private var depersonalization: Int = 0
    private var profReduction: Int = 0
    private var indexBurnout: Double = 0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTestBinding.inflate(inflater, container, false)

        mQuestionsList = getQuestions()

        binding.menuBtn.setOnClickListener {
            findNavController().navigate(R.id.action_testFragment_to_menuFragment)
        }

        binding.backBtn.isEnabled = false

        setQuestion()

        binding.submitBtn.setOnClickListener{
            val checkedRadioButtonId = binding.radioGroup.checkedRadioButtonId
            val selectedRadioButton = binding.root.findViewById<RadioButton>(checkedRadioButtonId)

            when(checkedRadioButtonId){
                -1 -> {
                    Toast.makeText(requireView().context,"Пожалуйста, выберите ответ", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    answers.add(mCurrentPosition-1, selectedRadioButton.text.toString())
                    mCurrentPosition++

                    when{
                        mCurrentPosition <= mQuestionsList!!.size -> {
                            setQuestion()
                        }
                        else -> {
                            testResult(answers)

                            setFragmentResult("resultData", bundleOf("indexBurnout" to indexBurnout,
                                "profReduction" to profReduction, "emotionalExhaustion" to emotionalExhaustion, "depersonalization" to depersonalization))

                            findNavController().navigate(R.id.action_testFragment_to_resultFragment)
                        }
                    }
                    binding.radioGroup.clearCheck()
                    binding.radioGroup.jumpDrawablesToCurrentState()
                }
            }
            binding.backBtn.isEnabled = mCurrentPosition != 1
        }

        binding.backBtn.setOnClickListener{
            mCurrentPosition--

            answers.removeAt(answers.lastIndex)

            if(mCurrentPosition==1){
                binding.backBtn.isEnabled = false
            }

            val question = mQuestionsList!![mCurrentPosition - 1]

            binding.progressBar.progress = mCurrentPosition

            binding.textViewProgress.text = "$mCurrentPosition/${binding.progressBar.max}"

            binding.textViewQuestion.text = question.question
        }

        return binding.root
    }

    private fun setQuestion() {

        val question = mQuestionsList!![mCurrentPosition - 1]

        if (mCurrentPosition == mQuestionsList!!.size) {
            binding.submitBtn.text = "Результат"
        } else {
            binding.submitBtn.text = "Далее"
        }

        binding.progressBar.progress = mCurrentPosition

        binding.textViewProgress.text = "$mCurrentPosition/${binding.progressBar.max}"

        binding.textViewQuestion.text = question.question
    }

    private fun testResult(answers: ArrayList<String>) {

        answers.forEach{
            when (it) {
                "Никогда" -> {answersNum.add(0)}
                "Очень редко" -> {answersNum.add(1)}
                "Редко" -> {answersNum.add(2)}
                "Иногда" -> {answersNum.add(3)}
                "Часто" -> {answersNum.add(4)}
                "Очень часто" -> {answersNum.add(5)}
                "Каждый день" -> {answersNum.add(6)}
            }
        }

        emotionalExhaustion = answersNum[0] + answersNum[1] + answersNum[2] + answersNum[5] + answersNum[7] + answersNum[12] + answersNum[13] + answersNum[15] + answersNum[19]
        depersonalization = answersNum[4] + answersNum[9] + answersNum[10] + answersNum[14] + answersNum[21]
        profReduction = answersNum[3] + answersNum[6] + answersNum[8] + answersNum[11] + answersNum[16] + answersNum[17] + answersNum[18] + answersNum[20]
        indexBurnout = roundOffDecimal(sqrt((((0-emotionalExhaustion.toDouble())/54).pow(2.0) + ((0-depersonalization.toDouble())/30).pow(2.0) + ((48-profReduction.toDouble())/48).pow(2.0))/3))
    }

    private fun getQuestions(): ArrayList<Question>{
        val list = ArrayList<Question>()

        val jsonData = resources.openRawResource(R.raw.questions).bufferedReader().use{it.readText()}

        val jsonStr = JSONObject(jsonData)

        val questions = jsonStr.getJSONArray("questions") as JSONArray

        for(i in 0 until questions.length()){
            val id = questions.getJSONObject(i).getInt("questionId")
            val text = questions.getJSONObject(i).getString("text")
            list.add(Question(id,text))
        }

        return list
    }

    private fun roundOffDecimal(number: Double): Double {
        return (number * 100).roundToInt().toDouble() / 100
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}