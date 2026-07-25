package com.aneesh.weather.core.util

import kotlin.math.roundToInt

fun Double.formatPressure(): String {
    return "${roundToInt()} mb"
}