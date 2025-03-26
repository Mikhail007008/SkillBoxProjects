package com.example.homework.ui.photoList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.homework.R
import com.example.homework.databinding.FragmentPhotoListBinding
import com.example.homework.db.App
import kotlinx.coroutines.launch

class PhotoListFragment : Fragment() {
    private var _binding: FragmentPhotoListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PhotoViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val db = (requireActivity().application as App).db
                return PhotoViewModel(db.albumDao()) as T
            }
        }
    }

    private val adapter = PhotoAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhotoListBinding.inflate(inflater, container, false)
        binding.recyclerView.adapter = adapter

        lifecycleScope.launch {
            viewModel.photos.collect { adapter.submitList(it) }
        }

        binding.createPhotoButton.setOnClickListener {
            findNavController().navigate(R.id.action_nav_photo_list_to_nav_snapshot)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
