package com.example.homework

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.homework.databinding.ActivityMainBinding
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: UserViewModel
    private lateinit var uiUpdate: UIUpdate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        uiUpdate = UIUpdate(binding)
        viewModel = ViewModelProvider(this)[UserViewModel::class.java]

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { updateUI(it) }
            }
        }

        binding.buttonRefresh.setOnClickListener {
            viewModel.fetchUserInfo()
        }
    }

    private fun updateUI(state: UIState) {
        when (state) {
            is UIState.Loading -> uiUpdate.setLoadingState()
            is UIState.Success -> uiUpdate.setSuccessState(state.user)
            is UIState.Error -> uiUpdate.setErrorState(state.message)
        }
    }
}

