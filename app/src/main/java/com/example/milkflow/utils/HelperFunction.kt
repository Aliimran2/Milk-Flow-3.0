package com.example.milkflow.utils

import java.math.BigDecimal
import java.math.RoundingMode

fun Double.formatToTwoDecimalPlaces() : Double {
    return BigDecimal(this).setScale(2, RoundingMode.HALF_UP).toDouble()
}