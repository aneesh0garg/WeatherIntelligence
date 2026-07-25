package com.aneesh.weather.util

import kotlin.math.roundToInt

fun Double.formatPressure(): String {
    return "${roundToInt()} mb"
}