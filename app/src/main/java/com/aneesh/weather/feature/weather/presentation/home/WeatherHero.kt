package com.aneesh.weather.feature.weather.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.aneesh.weather.feature.weather.domain.model.Weather
import com.aneesh.weather.feature.weather.presentation.components.CurrentWeatherCard

@Composable
fun WeatherHero(
    weather: Weather,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit,
    containerColor: Color
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        CurrentWeatherCard(
            weather = weather,
            isFavorite = isFavorite,
            onToggleFavorite = onToggleFavorite,
            containerColor = containerColor,
            contentColor = Color.White
        )
    }
}

fun String.weatherColors(): List<Color> {
    val condition = lowercase()
    return when {
        condition.contains("thunder") || condition.contains("storm") -> listOf(Color(0xFF17223B), Color(0xFF40516E))
        condition.contains("rain") || condition.contains("drizzle") -> listOf(Color(0xFF1E5D8E), Color(0xFF6CB4D8))
        condition.contains("snow") || condition.contains("sleet") -> listOf(Color(0xFF7896AE), Color(0xFFC3D8E8))
        condition.contains("cloud") || condition.contains("overcast") || condition.contains("fog") -> listOf(Color(0xFF586A7D), Color(0xFF9AAAB8))
        condition.contains("sun") || condition.contains("clear") -> listOf(Color(0xFFFFA84B), Color(0xFFEF6C4E))
        else -> listOf(Color(0xFF315C93), Color(0xFF6A9DD0))
    }
}
