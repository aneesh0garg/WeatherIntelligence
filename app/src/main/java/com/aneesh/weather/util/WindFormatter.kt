package com.aneesh.weather.util

import kotlin.math.roundToInt

fun Double.formatWindSpeed(): String = "${roundToInt()} km/h"
