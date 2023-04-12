package com.rajit.dynamicbackground.util

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View

/*
*
* Custom Gradient Background using Extension Function
* @param dominantColor represents the color integer of the dominant color from Bitmap
*
* */
fun View.linearGradientBackground(dominantColor: Int): GradientDrawable {

    return GradientDrawable().apply {
        colors = intArrayOf(
            dominantColor,
            Color.parseColor("#2E2929"),
            Color.parseColor("#171616")
        )
        gradientType = GradientDrawable.LINEAR_GRADIENT
        orientation = GradientDrawable.Orientation.TOP_BOTTOM
    }

}