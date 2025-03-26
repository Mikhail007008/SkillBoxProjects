package com.example.homework

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.LinearLayout
import androidx.core.view.setPadding
import com.example.homework.databinding.SocialBlockViewBinding

@SuppressLint("Recycle")
class SocialBlockView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private val binding: SocialBlockViewBinding

    init {
        orientation = VERTICAL
        val paddingInDp = 16f
        val r: Resources = resources
        val paddingInPixels = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            paddingInDp,
            r.displayMetrics
        )

        setPadding(paddingInPixels.toInt())

        /*
        Попытка задать Margin здесь, как-то не очень вышло...

        val marginInDp = 16f
        val marginInPixels = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            marginInDp,
            r.displayMetrics
        ).toInt()

        for (i in 0 until childCount) {
            val child = getChildAt(i)
            val layoutParams = child.layoutParams as? MarginLayoutParams
            layoutParams?.setMargins(marginInPixels, marginInPixels, marginInPixels, marginInPixels)
            child.layoutParams = layoutParams
        }
         */

        val inflatedView = inflate(context, R.layout.social_block_view, this)
        binding = SocialBlockViewBinding.bind(inflatedView)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SocialBlockView, 0, 0)

        val topLineText = typedArray.getString(R.styleable.SocialBlockView_top_line_text)
        binding.topLine.text = topLineText

        val bottomLineText = typedArray.getString(R.styleable.SocialBlockView_bottom_line_text)
        binding.bottomLine.text = bottomLineText

        typedArray.recycle()
    }

    fun setTopLineText(text: String) {
        binding.topLine.text = text
    }

    fun setBottomLineText(text: String) {
        binding.bottomLine.text = text
    }
}


