package com.example.homework.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.homework.databinding.PhotoItemFullscreenBinding
import com.example.homework.data.Photo

class FullscreenPhotoAdapter(
    private val photos: List<Photo>
) : RecyclerView.Adapter<FullscreenPhotoAdapter.PhotoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding = PhotoItemFullscreenBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(photos[position])
    }

    override fun getItemCount(): Int = photos.size

    class PhotoViewHolder(private val binding: PhotoItemFullscreenBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(photo: Photo) = with(binding) {
            Glide.with(photoView.context)
                .load(photo.imgSrc.replace("http://", "https://"))
                .into(photoView)

            cameraTextView.text = "Camera: ${photo.camera.name}"
            roverTextView.text = "Rover: ${photo.rover.name}"
            solTextView.text = "Sol: ${photo.sol}"
            dateTextView.text = "Date: ${photo.earthDate}"
        }
    }
}
