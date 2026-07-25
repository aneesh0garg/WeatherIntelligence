package com.aneesh.weather.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun String.toDisplayTime(): String = try {
    val input = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    val output = SimpleDateFormat("h a", Locale.getDefault())
    input.parse(this)?.let(output::format) ?: this
} catch (e: Exception) {
    e.printStackTrace()
    this
}

fun String.toForecastDayLabel(): String = try {
    val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(this)
        ?: throw IllegalArgumentException("Invalid forecast date")
    val today = Calendar.getInstance()
    val forecastDay = Calendar.getInstance().apply { time = date }
    if (
        today.get(Calendar.YEAR) == forecastDay.get(Calendar.YEAR) &&
        today.get(Calendar.DAY_OF_YEAR) == forecastDay.get(Calendar.DAY_OF_YEAR)
    ) {
        "Today"
    } else {
        SimpleDateFormat("EEEE", Locale.getDefault()).format(date)
    }
} catch (e: Exception) {
    e.printStackTrace()
    this
}
