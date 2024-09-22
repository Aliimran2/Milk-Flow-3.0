package com.example.milkflow.utils

import android.graphics.Color

object ColorsUtil {
    val colors = listOf(
        Color.parseColor("#FFCDD2"), // Light Red
        Color.parseColor("#F8BBD0"), // Pink
        Color.parseColor("#E1BEE7"), // Purple
        Color.parseColor("#C5CAE9"), // Indigo
        Color.parseColor("#BBDEFB"), // Blue
        Color.parseColor("#C8E6C9"), // Green
        Color.parseColor("#FFCCBC"), // Orange
        Color.parseColor("#D7CCC8")  // Brown
    )

    fun getRandomColorFromPalette(): Int {
        return colors.random() // Randomly pick one from the list
    }




    fun getRandomColor(): Int {
        val random = java.util.Random()
        val red = random.nextInt(156) + 100
        val green = random.nextInt(156) + 100
        val blue = random.nextInt(156) + 100
        return Color.rgb(red, green, blue)
    }


}