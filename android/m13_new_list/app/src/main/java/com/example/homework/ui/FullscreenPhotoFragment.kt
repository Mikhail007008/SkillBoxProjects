package com.example.homework.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.example.homework.databinding.FragmentFullscreenPhotoBinding
import com.example.homework.data.Photo

class FullscreenPhotoFragment : DialogFragment() {
    private var _binding: FragmentFullscreenPhotoBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: FullscreenPhotoAdapter
    private var photos: List<Photo> = emptyList()
    private var startPosition: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFullscreenPhotoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        photos = arguments?.getParcelableArrayList(ARG_PHOTOS) ?: emptyList()
        startPosition = arguments?.getInt(ARG_POSITION) ?: 0

        adapter = FullscreenPhotoAdapter(photos)
        binding.viewPager.adapter = adapter
        binding.viewPager.setCurrentItem(startPosition, false)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        private const val ARG_PHOTOS = "photos"
        private const val ARG_POSITION = "position"

        fun newInstance(photos: List<Photo>, position: Int): FullscreenPhotoFragment {
            return FullscreenPhotoFragment().apply {
                arguments = bundleOf(
                    ARG_PHOTOS to ArrayList(photos),
                    ARG_POSITION to position
                )
            }
        }
    }
}
