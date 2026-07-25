package com.aneesh.weather.core.util

import kotlin.math.roundToInt

fun Double.formatWindSpeed(): String = "${roundToInt()} km/h"
