package com.aneesh.weather.core.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

private val inputFormatter =
    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

private val outputFormatter =
    DateTimeFormatter.ofPattern("h:mm a", Locale.getDefault())

fun String.toDisplayTime(): String {

    return try {

        LocalDateTime
            .parse(this, inputFormatter)
            .format(outputFormatter)

    } catch (e: Exception) {

        this

    }

}