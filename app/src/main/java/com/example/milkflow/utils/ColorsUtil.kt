package com.example.milkflow.utils

import android.graphics.Color

object ColorsUtil {
    val colors = listOf(


        Color.parseColor("#FFD700"),
        Color.parseColor("#FF8C00"),
        Color.parseColor("#00FF00"),
        Color.parseColor("#FF1493"),

    )

    fun getRandomColorFromPalette(): Int {
        return colors.random() // Randomly pick one from the list
    }





}