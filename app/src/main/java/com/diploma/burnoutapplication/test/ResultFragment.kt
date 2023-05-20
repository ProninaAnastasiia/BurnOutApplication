package com.diploma.burnoutapplication.test

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.diploma.burnoutapplication.R
import com.diploma.burnoutapplication.databinding.FragmentResultBinding


class ResultFragment : Fragment() {
    private var _binding: FragmentResultBinding?= null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)

        setFragmentResultListener("resultData") { _, bundle ->
            val emotionalExhaustion = bundle.getInt("emotionalExhaustion")
            val depersonalization = bundle.getInt("depersonalization")
            val profReduction = bundle.getInt("profReduction")
            val burnout = bundle.getDouble("indexBurnout")

            binding.progressBar1.progress = emotionalExhaustion
            binding.textViewProgress1.text = "$emotionalExhaustion/${binding.progressBar1.max}"

            binding.progressBar2.progress = depersonalization
            binding.textViewProgress2.text = "$depersonalization/${binding.progressBar2.max}"

            binding.progressBar3.progress = profReduction
            binding.textViewProgress3.text = "$profReduction/${binding.progressBar3.max}"

            binding.textViewIndexBurnout.text = "$burnout"

            when (emotionalExhaustion) {
                in 0..16 -> binding.tvEmotionalExhaustion.text = "Эмоциональное истощение (слабое)"
                in 17..26 -> binding.tvEmotionalExhaustion.text = "Эмоциональное истощение (среднее)"
                in 27..54 -> binding.tvEmotionalExhaustion.text = "Эмоциональное истощение (сильное)"
            }

            when (depersonalization) {
                in 0..6 -> binding.tvDepersonalization.text = "Деперсонализация (слабая)"
                in 7..12 -> binding.tvDepersonalization.text = "Деперсонализация (средняя)"
                in 13..30 -> binding.tvDepersonalization.text = "Деперсонализация (сильная)"
            }

            when (profReduction) {
                in 39..48 -> binding.tvProfReduction.text = "Редукция профессионализма (слабая)"
                in 32..38 -> binding.tvProfReduction.text = "Редукция профессионализма (средняя)"
                in 0..31 -> binding.tvProfReduction.text = "Редукция профессионализма (сильная)"
            }

            showAbout(binding.aboutEmotionalExhaustionBtn, "Эмоциональное истощение", "Эмоциональное истощение рассматривается как основная составляющая выгорания и проявляется в переживаниях сниженного эмоционального тонуса, повышенной психической истощаемости и аффективной лабильности, утраты интереса и позитивных чувств к окружающим, ощущении «пресыщенности» работой, неудовлетворенностью жизнью в целом.")
            showAbout(binding.aboutDepersonalizationBtn, "Деперсонализация", "Деперсонализация проявляется в эмоциональном отстранении и безразличии, формальном выполнении профессиональных обязанностей без личностной включенности и сопереживания, а в отдельных случаях – в негативизме и циничном отношении. В контексте синдрома перегорания «деперсонализация» предполагает формирование особых, деструктивных взаимоотношений с окружающими людьми.")
            showAbout(binding.aboutProfReductionBtn, "Редукция профессионализма", "Редукция профессиональных достижений отражает степень удовлетворенности человека собой как личностью и как профессионалом. Неудовлетворительное значение этого показателя отражает тенденцию к негативной оценке своей компетентности и продуктивности и, как следствие, – снижение профессиональной мотивации, нарастание негативизма в отношении служебных обязанностей, тенденцию к снятию с себя ответственности, к изоляции от окружающих, отстраненность и неучастие, избегание работы сначала психологически, а затем физически.")

            when (burnout) {
                in 0.0..0.3 -> {
                    binding.textViewIndexBurnout.setTextColor(Color.rgb(200,216,62))
                    binding.textViewDetails.text = "Не похоже, что у Вас есть эмоциональное выгорание. Однако, профилактика никогда не повредит. Попробуйте воспользоваться предложенными инструментами, возможно, Вам понравится сам процесс."}
                in 0.3..0.6 -> {
                    binding.textViewIndexBurnout.setTextColor(Color.rgb(243,207,45))
                    binding.textViewDetails.text = "Возможно, у Вас есть эмоциональное выгорание. Попробуйте использовать предложенные инструменты, чтобы почувствовать себя лучше."}
                in 0.6..0.8 -> {
                    binding.textViewIndexBurnout.setTextColor(Color.rgb(253,77,82))
                    binding.textViewDetails.text = "Похоже, что у Вас есть эмоциональное выгорание. Попробуйте бороться с ним, используя все доступные средства. Всё получится!"}
                in 0.8..1.0 -> {
                    binding.textViewIndexBurnout.setTextColor(Color.rgb(179,17,22))
                    binding.textViewDetails.text = "Похоже, что у Вас эмоциональное выгорание на серьезной стадии. Возможно, сейчас лучше обратиться к специалистам по психическому здоровью."}
            }
        }

        binding.finishBtn.setOnClickListener {
            findNavController().navigate(R.id.action_resultFragment_to_mainFragment)
        }

        return binding.root
    }

    private fun showAbout(btn: ImageButton, title: String, message: String){
        btn.setOnClickListener {
            val builder = AlertDialog.Builder(requireView().context)

            with(builder) {
                setTitle(title)
                setMessage(message)
                setPositiveButton("Понятно"){dialogInterface, it -> dialogInterface.cancel()}
                show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}