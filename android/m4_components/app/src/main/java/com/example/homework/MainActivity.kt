package com.example.homework

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.homework.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
    }

    @SuppressLint("SetTextI18n")
    private fun initUI() = with(binding) {
        progress.progress = Random.nextInt(101)
        numberOfPointsTextView.text = "${progress.progress}/100"

        noticeSwitch.setOnCheckedChangeListener { _, isChecked ->
            authorizationCheckbox.isEnabled = isChecked
            newsCheckbox.isEnabled = isChecked
            if (!isChecked) {
                authorizationCheckbox.isChecked = false
                newsCheckbox.isChecked = false
            }
            save()
        }

        authorizationCheckbox.setOnCheckedChangeListener { _, _ ->
            save()
        }

        newsCheckbox.setOnCheckedChangeListener { _, _ ->
            save()
        }

        saveButton.setOnClickListener {
            Snackbar.make(it, R.string.save_button, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun save() = with(binding) {
        saveButton.isEnabled = !nameInputText.text.isNullOrEmpty() &&
                !telephoneInputText.text.isNullOrEmpty() &&
                (binding.authorizationCheckbox.isChecked || binding.newsCheckbox.isChecked)
    }
}