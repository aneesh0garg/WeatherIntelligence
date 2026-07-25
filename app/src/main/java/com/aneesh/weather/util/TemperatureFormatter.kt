package com.aneesh.weather.util

import kotlin.math.roundToInt

fun Double.formatTemperature(): String {
    return "${roundToInt()}°"
}