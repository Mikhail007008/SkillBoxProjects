package com.example.homework

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.homework.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var repository: Repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewText = binding.resultTextview
        repository = Repository(this)

        if (savedInstanceState != null) {
            val savedText = savedInstanceState.getString("SAVED_TEXT")
            viewText.text = savedText ?: ""
        } else {
            viewText.text = repository.getText()
        }

        binding.saveButton.setOnClickListener {
            val text = binding.inputTextEdit.text.toString()
            repository.saveText(text)
            viewText.text = text
        }

        binding.clearButton.setOnClickListener {
            repository.clearText()
            viewText.text = ""
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("SAVED_TEXT", binding.resultTextview.text.toString())
    }
}