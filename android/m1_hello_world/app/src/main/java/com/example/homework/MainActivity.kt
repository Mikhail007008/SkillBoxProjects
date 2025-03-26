package com.example.homework

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import com.example.homework.databinding.ActivityMainBinding

class MainActivity : ComponentActivity() {
    private var count = 0
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        updateUI()

        binding.minusButton.setOnClickListener {
            count--
            updateUI()
        }

        binding.plusButton.setOnClickListener {
            count++
            updateUI()
        }

        binding.resetButton.setOnClickListener {
            resetState()
        }
    }

    private fun updateUI() {
        binding.countTextView.text = count.toString()

        when (count) {
            0 -> setState(
                text = "Все места свободны",
                colorRes = R.color.green,
                minusEnabled = false,
                plusEnabled = true,
                resetVisible = false
            )

            in 1..49 -> setState(
                text = "Осталось мест: ${50 - count}",
                colorRes = R.color.blue,
                minusEnabled = true,
                plusEnabled = true,
                resetVisible = false
            )

            50 -> setState(
                text = "Пассажиров слишком много!",
                colorRes = R.color.red,
                minusEnabled = true,
                plusEnabled = false,
                resetVisible = true
            )
        }
    }

    private fun setState(
        text: String,
        colorRes: Int,
        minusEnabled: Boolean,
        plusEnabled: Boolean,
        resetVisible: Boolean
    ){
        binding.someText.text = text
        binding.someText.setTextColor(resources.getColor(colorRes, theme))
        binding.minusButton.isEnabled = minusEnabled
        binding.plusButton.isEnabled = plusEnabled
        binding.resetButton.visibility = if (resetVisible) View.VISIBLE else View.INVISIBLE
    }

    private fun resetState() {
        count = 0
        updateUI()
    }
}
