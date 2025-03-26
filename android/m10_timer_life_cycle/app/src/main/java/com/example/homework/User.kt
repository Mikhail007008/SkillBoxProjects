package com.example.homework

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class User(
    val lname: String,
    val fname: String,
    val sname: String?
) : Parcelable {
}