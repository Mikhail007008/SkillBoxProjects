package com.example.homework.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.homework.viewModel.PhotoListViewModel
import com.example.homework.databinding.FragmentNasaPhotoListBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlin.math.max

class PhotoListFragment : Fragment() {
    private var _binding: FragmentNasaPhotoListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PhotoListViewModel by viewModels()

    private val photoAdapter = PhotoAdapter { photos, position ->
        FullscreenPhotoFragment.newInstance(photos, position).show(parentFragmentManager, "fullscreenPhoto")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNasaPhotoListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val displayMetrics = resources.displayMetrics
        val screenWidth = displayMetrics.widthPixels
        val imageSize = 500

        val spanCount = max(2, screenWidth / imageSize)

        binding.recycler.apply {
            layoutManager = GridLayoutManager(context, spanCount)

            adapter = photoAdapter
        }

        viewModel.pagedPhoto.onEach {
            pagingData -> photoAdapter.submitData(lifecycle, pagingData)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refresh()
        }

        viewModel.isLoading.onEach {
            binding.swipeRefresh.isRefreshing = it
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}