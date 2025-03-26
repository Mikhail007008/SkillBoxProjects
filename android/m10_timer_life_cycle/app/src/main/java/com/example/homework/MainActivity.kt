package com.example.homework

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.homework.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val fioText = "fio_text"
    private lateinit var binding: ActivityMainBinding

    @SuppressLint("SetTextI18n")
    private val authLauncher =
        registerForActivityResult(AuthActivity.AuthResultContract()) { user ->
            user?.let {
                if (it.sname != null) {
                    binding.fioText.text = "${it.lname} ${it.fname} ${it.sname}"
                } else binding.fioText.text = "${it.lname} ${it.fname}"
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("ДЗ6", "Основная OnCreate")

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        savedInstanceState?.getString(fioText)?.let { restoredText ->
            Log.d("ДЗ6", "onRestoreInstanceState")
            binding.fioText.text = restoredText
        }

        binding.authorisationButton.setOnClickListener {
            val hasSname = binding.snameCheckbox.isChecked
            authLauncher.launch(hasSname)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("ДЗ6", "Основная onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("ДЗ6", "Основная onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("ДЗ6", "Основная onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("ДЗ6", "Основная onStop")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d("ДЗ6", "Основная onSaveInstanceState")
        outState.putString(fioText, binding.fioText.text.toString())
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("ДЗ6", "Основная onDestroy")
    }
}

/*
2024-12-17 16:02:15.593 14097-14097 ДЗ6                     com.example.homework                 D  Основная OnCreate
2024-12-17 16:02:15.851 14097-14097 ДЗ6                     com.example.homework                 D  Основная onStart
2024-12-17 16:02:15.859 14097-14097 ДЗ6                     com.example.homework                 D  Основная onResume
2024-12-17 16:02:41.295 14097-14097 ДЗ6                     com.example.homework                 D  Основная onPause
2024-12-17 16:02:41.360 14097-14097 ДЗ6                     com.example.homework                 D  Авторизация OnCreate
2024-12-17 16:02:41.640 14097-14097 ДЗ6                     com.example.homework                 D  Авторизация onStart
2024-12-17 16:02:41.645 14097-14097 ДЗ6                     com.example.homework                 D  Авторизация onResume
2024-12-17 16:02:42.480 14097-14097 ДЗ6                     com.example.homework                 D  Основная onStop
2024-12-17 16:02:42.485 14097-14097 ДЗ6                     com.example.homework                 D  Основная onSaveInstanceState
2024-12-17 16:03:16.044 14097-14097 ДЗ6                     com.example.homework                 D  Авторизация onPause
2024-12-17 16:03:16.080 14097-14097 ДЗ6                     com.example.homework                 D  Основная onStart
2024-12-17 16:03:16.087 14097-14097 ДЗ6                     com.example.homework                 D  Основная onResume
2024-12-17 16:03:16.765 14097-14097 ДЗ6                     com.example.homework                 D  Авторизация onStop
2024-12-17 16:03:16.768 14097-14097 ДЗ6                     com.example.homework                 D  Авторизация onDestroy
 */