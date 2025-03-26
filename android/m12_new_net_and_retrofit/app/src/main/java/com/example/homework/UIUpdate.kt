package com.example.homework

import android.annotation.SuppressLint
import android.view.View
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.homework.databinding.ActivityMainBinding

class UIUpdate(private val binding: ActivityMainBinding) {
    fun setLoadingState() {
        setVisibilityAndColor(
            View.VISIBLE,
            View.GONE,
            View.GONE,
            com.google.android.material.R.color.cardview_shadow_end_color
        )
    }

    @SuppressLint("SetTextI18n", "PrivateResource")
    fun setSuccessState(user: UserInfoModel) {
        setVisibilityAndColor(
            View.GONE,
            View.VISIBLE,
            View.VISIBLE,
            com.google.android.material.R.color.design_dark_default_color_primary
        )

        binding.textInfoUser.text = """
                        Данные пользователя:
                        Пол - ${user.gender} 
                        Имя - ${user.name.first} 
                        Фамилия - ${user.name.last}
                        Город - ${user.location.city}
                        Штат - ${user.location.state}
                        Страна - ${user.location.country}
                    """.trimIndent()

        Glide.with(binding.root.context)
            .load(user.picture.large)
            .into(binding.imageUser)
    }

    fun setErrorState(message: String) {
        setVisibilityAndColor(
            View.GONE,
            View.VISIBLE,
            View.GONE,
            com.google.android.material.R.color.error_color_material_light
        )
        binding.textInfoUser.text = message
    }

    private fun setVisibilityAndColor(progress: Int, text: Int, image: Int, color: Int) {
        binding.progressCircular.visibility = progress
        binding.textInfoUser.visibility = text
        binding.imageUser.visibility = image
        binding.buttonRefresh.setBackgroundColor(ContextCompat.getColor(binding.root.context, color))
    }
}