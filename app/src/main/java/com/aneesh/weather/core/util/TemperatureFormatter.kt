package com.aneesh.weather.core.util

import kotlin.math.roundToInt

fun Double.formatTemperature(): String {
    return "${roundToInt()}°"
}