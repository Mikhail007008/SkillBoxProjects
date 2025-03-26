package com.example.homework.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Photo(
    val id: Int,
    val sol: Int,
    val camera: CameraInfo,
    val imgSrc: String,
    val earthDate: String,
    val rover: RoverInfo
) : Parcelable

@Parcelize
data class CameraInfo(
    val name: String
) : Parcelable

@Parcelize
data class RoverInfo(
    val name: String
) : Parcelable