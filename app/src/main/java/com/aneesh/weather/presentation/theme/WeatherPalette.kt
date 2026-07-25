package com.aneesh.weather.feature.weather.presentation.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class WeatherPalette(
    val background: List<Color>,
    val cardContainer: Color,
    val content: Color,
    val mutedContent: Color,
    val rain: Color,
    val chartLine: Color,
    val chartGrid: Color
)

private val DefaultWeatherPalette = WeatherPalette(
    background = listOf(Color(0xFF315C93), Color(0xFF6A9DD0)),
    cardContainer = Color(0xFF6A9DD0),
    content = Color.White,
    mutedContent = Color(0xCCFFFFFF),
    rain = Color(0xFF59C8FF),
    chartLine = Color.White,
    chartGrid = Color(0x59FFFFFF)
)

val LocalWeatherPalette = staticCompositionLocalOf { DefaultWeatherPalette }

fun String.toWeatherPalette(): WeatherPalette {
    val background = when {
        contains("thunder", true) || contains("storm", true) -> listOf(Color(0xFF17223B), Color(0xFF40516E))
        contains("rain", true) || contains("drizzle", true) -> listOf(Color(0xFF1E5D8E), Color(0xFF6CB4D8))
        contains("snow", true) || contains("sleet", true) -> listOf(Color(0xFF7896AE), Color(0xFFC3D8E8))
        contains("cloud", true) || contains("overcast", true) || contains("fog", true) -> listOf(Color(0xFF586A7D), Color(0xFF9AAAB8))
        contains("sun", true) || contains("clear", true) -> listOf(Color(0xFFFFA84B), Color(0xFFEF6C4E))
        else -> DefaultWeatherPalette.background
    }
    return DefaultWeatherPalette.copy(background = background, cardContainer = background.last())
}
