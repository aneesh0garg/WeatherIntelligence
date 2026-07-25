package com.aneesh.weather.feature.weather.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.aneesh.weather.feature.weather.domain.model.Weather

@Composable
fun WeatherHero(
    weather: Weather,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        CurrentWeatherCard(
            weather = weather,
            isFavorite = isFavorite,
            onToggleFavorite = onToggleFavorite
        )
    }
}
