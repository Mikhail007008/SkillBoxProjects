package com.example.homework.fragments

import android.animation.TimeInterpolator
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.homework.R
import com.example.homework.databinding.FragmentSurveyBinding
import com.example.homework.quiz.QuizStorage
import java.util.Locale

class SurveyFragment : Fragment() {
    private lateinit var selectedAnswers: MutableMap<Int, Int>
    private var _binding: FragmentSurveyBinding? = null
    private val binding get() = _binding!!
    private val deviceLocale = when (Locale.getDefault().language) {
        "ru" -> QuizStorage.Locale.Ru
        else -> QuizStorage.Locale.En
    }

    companion object {
        private const val SELECTED_ANSWERS = "SELECTED_ANSWERS"
        private const val QUIZ_RESULT = "QUIZ_RESULT"
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSurveyBinding.inflate(inflater, container, false)

        selectedAnswers = if (savedInstanceState != null) {
            savedInstanceState.getSerializable(SELECTED_ANSWERS) as? MutableMap<Int, Int>
                ?: mutableMapOf()
        } else {
            mutableMapOf()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        animateView(binding.questionsContainer, 2000L, 1f, AccelerateInterpolator())
        loadQuiz()

        binding.buttonSendResult.setOnClickListener {
            navigateToNextFragment()
        }

        binding.buttonPrevious.setOnClickListener {
            findNavController().navigate(R.id.action_surveyFragment_to_mainFragment)
        }
    }

    private fun loadQuiz() {
        binding.questionsContainer.removeAllViews()
        val quiz = QuizStorage.getQuiz(deviceLocale)

        quiz.questions.forEachIndexed { questionIndex, question ->
            val questionTextView = TextView(requireContext()).apply {
                text = question.question
                textSize = 20f
                setPadding(0, 15, 0, 8)
            }
            binding.questionsContainer.addView(questionTextView)

            val radioGroup = RadioGroup(requireContext()).apply {
                orientation = RadioGroup.VERTICAL
                id = View.generateViewId()
            }

            question.answers.forEachIndexed { _, answer ->
                val radioButton = RadioButton(requireContext()).apply {
                    text = answer
                    id = View.generateViewId()
                }
                radioGroup.addView(radioButton)
            }

            val savedAnswerIndex = selectedAnswers[questionIndex]
            if (savedAnswerIndex != null && savedAnswerIndex in 0 until radioGroup.childCount) {
                (radioGroup.getChildAt(savedAnswerIndex) as RadioButton).isChecked = true
            }

            radioGroup.setOnCheckedChangeListener { _, checkedId ->
                val selectedIndex = radioGroup.indexOfChild(radioGroup.findViewById(checkedId))
                if (selectedIndex != -1) {
                    selectedAnswers[questionIndex] = selectedIndex
                }
            }

            binding.questionsContainer.addView(radioGroup)
        }
    }

    private fun navigateToNextFragment() {
        val selectedQuiz = QuizStorage.getQuiz(deviceLocale)

        if (selectedAnswers.size != selectedQuiz.questions.size) {
            Toast.makeText(requireContext(), R.string.text_toast, Toast.LENGTH_SHORT)
                .show()
            return
        }

        val selectedAnswersList = selectedAnswers.values.toList()
        val result = QuizStorage.answer(selectedQuiz, selectedAnswersList)
        val bundle = Bundle().apply {
            putString(QUIZ_RESULT, result)
        }
        findNavController().navigate(R.id.action_surveyFragment_to_resultFragment, bundle)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(SELECTED_ANSWERS, HashMap(selectedAnswers))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun animateView(view: View, duration: Long, endAlpha: Float, interpolator: TimeInterpolator) {
        view.animate().apply {
            this.duration = duration
            alpha(endAlpha)
            this.interpolator = interpolator
        }.start()
    }
}