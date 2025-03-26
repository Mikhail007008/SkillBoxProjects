package com.example.homework

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.homework.databinding.FragmentMainBinding
import kotlinx.coroutines.launch

class MainFragment : Fragment(R.layout.fragment_main) {
    private val viewModel by viewModels<MainViewModel>()
    private var _viewBinding: FragmentMainBinding? = null
    private val viewBinding get() = _viewBinding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _viewBinding = FragmentMainBinding.bind(view)

        with(viewBinding) {
            editRequest.doAfterTextChanged { requestTextEntered ->
                viewModel.requestTextEntered(requestTextEntered?.toString().orEmpty())
            }

            sendButton.setOnClickListener {
                val requestText = editRequest.text?.toString().orEmpty()
                viewModel.request(requestText)
            }
        }

        launchingCoroutines()
    }

    private fun launchingCoroutines() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiState.collect { uiState ->
                        with(viewBinding) {
                            sendButton.isEnabled = uiState.isButtonEnabled
                            progressCircular.isVisible = uiState.isProgressVisible
                        }
                    }
                }

                launch {
                    viewModel.requestText.collect { text ->
                        viewBinding.resultRequestText.text = text
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }
}