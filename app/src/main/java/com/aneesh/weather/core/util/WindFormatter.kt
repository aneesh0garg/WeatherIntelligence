package com.aneesh.weather.core.util

import kotlin.math.roundToInt

fun Double.formatWindSpeed(): String {
    return "${roundToInt()} km/h"
}