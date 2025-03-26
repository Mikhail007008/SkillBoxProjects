package com.example.homework.fragments

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.TextView
import androidx.activity.addCallback
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.homework.R
import com.example.homework.databinding.FragmentResultBinding

class ResultFragment : Fragment() {
    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val QUIZ_RESULT = "QUIZ_RESULT"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResultBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rotationAnimation = ObjectAnimator.ofFloat(binding.againButton, View.ROTATION_X, 0f, 360f).apply {
            duration = 1000
            interpolator = AccelerateDecelerateInterpolator()
        }

        val translationAnimation = ObjectAnimator.ofFloat(binding.resultText, View.TRANSLATION_Y, -200f, 200f).apply {
            duration = 5000
            repeatCount = 5
            repeatMode = ObjectAnimator.REVERSE
        }

        AnimatorSet().apply {
            playTogether(rotationAnimation, translationAnimation)
            start()
        }

        val result = arguments?.getString(QUIZ_RESULT)
        val resultTextView: TextView = view.findViewById(R.id.result_text)
        resultTextView.text = result

        binding.againButton.setOnClickListener {
            findNavController().navigate(R.id.action_resultFragment_to_surveyFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}