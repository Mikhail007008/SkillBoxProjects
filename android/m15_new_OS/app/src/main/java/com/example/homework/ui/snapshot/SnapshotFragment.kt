package com.example.homework.ui.snapshot

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.homework.R
import com.example.homework.databinding.FragmentPhotoSnapshotBinding
import com.example.homework.db.App
import com.example.homework.ui.photoList.PhotoViewModel
import kotlinx.coroutines.launch

class SnapshotFragment : Fragment() {
    private var _binding: FragmentPhotoSnapshotBinding? = null
    private val binding get() = _binding!!
    private val snapshotViewModel: SnapshotViewModel by viewModels()
    private val photoViewModel: PhotoViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val db = (requireActivity().application as App).db
                return PhotoViewModel(db.albumDao()) as T
            }
        }
    }

    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (permissions.all { it.value }) {
                startCamera()
            } else {
                Toast.makeText(requireContext(),
                    getString(R.string.toast_permission_not_granted), Toast.LENGTH_SHORT)
                    .show()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhotoSnapshotBinding.inflate(inflater, container, false)

        binding.takePhotoButton.setOnClickListener { snapshotViewModel.takePhoto() }

        lifecycleScope.launch {
            snapshotViewModel.photoUri.collect { uri ->
                uri?.let {
                    photoViewModel.addPhoto(it.toString())
                    findNavController().popBackStack()
                }
            }
        }

        checkPermissions()
        return binding.root
    }

    private fun checkPermissions() {
        val isAllGranted = REQUEST_PERMISSIONS.all { permission ->
            ContextCompat.checkSelfPermission(
                requireContext(),
                permission
            ) == PackageManager.PERMISSION_GRANTED
        }

        if (isAllGranted) {
            startCamera()
        } else {
            permissionLauncher.launch(REQUEST_PERMISSIONS)
        }
    }

    private fun startCamera() {
        snapshotViewModel.startCamera(viewLifecycleOwner, binding.viewFinder.surfaceProvider)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private val REQUEST_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }
}


