package com.example.homework

import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.example.homework.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class ViewSetup(
    private val binding: ActivityMainBinding,
    private val viewModel: MainViewModel,
    private val lifecycleOwner: LifecycleOwner
) {
    fun initUi() = with(binding){
        with(viewModel){
            addButton.setOnClickListener {
                val word = editWord.text.toString().trim()
                addWord((word))
                editWord.text?.clear()
            }

            deleteButton.setOnClickListener {
                clearAll()
            }

            lifecycleOwner.lifecycleScope.launch {
                repeatedWords.collect{ words ->
                    textViewDictionary.text = words.joinToString("\n") { "${it.word}: ${it.count}" }
                }
            }

            lifecycleOwner.lifecycleScope.launch {
                errorMessage.collect{ message ->
                    message?.let {
                        Toast.makeText(root.context, it, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}