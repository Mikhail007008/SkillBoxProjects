package com.example.homework.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.example.homework.R
import com.example.homework.databinding.DialogMarkerInfoBinding

class MarkerDialogBox : DialogFragment() {

    private var _binding: DialogMarkerInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogMarkerInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val name = arguments?.getString("name") ?: "Неизвестно"
        val description = arguments?.getString("description") ?: "Описание отсутствует"
        val imageUrl = arguments?.getString("imageUrl")

        binding.apply {
            markerName.text = name
            markerDescription.text = description

            if (!imageUrl.isNullOrEmpty()) {
                Glide.with(root.context)
                    .load(imageUrl)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(markerImage)
            } else {
                markerImage.setImageResource(R.drawable.ic_launcher_foreground)
            }
            closeButton.setOnClickListener { dismiss() }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(name: String, description: String, imageUrl: String?) =
            MarkerDialogBox().apply {
                arguments = bundleOf(
                    "name" to name,
                    "description" to description,
                    "imageUrl" to imageUrl
                )
            }
    }
}




