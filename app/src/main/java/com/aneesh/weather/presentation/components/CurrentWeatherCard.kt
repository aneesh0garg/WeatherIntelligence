package com.aneesh.weather.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.aneesh.weather.domain.model.Weather
import com.aneesh.weather.presentation.theme.LocalWeatherPalette
import com.aneesh.weather.util.formatTemperature

@Composable
fun CurrentWeatherCard(
    weather: Weather,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit
) {
    val palette = LocalWeatherPalette.current
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = palette.cardContainer,
            contentColor = palette.content
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                weather.city,
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(
                Modifier.height(16.dp)
            )

            AsyncImage(
                model = "https:${weather.icon}",
                contentDescription = null,
                modifier = Modifier.size(96.dp)

            )

            Text(
                weather.temperature.formatTemperature(),
                style = MaterialTheme.typography.displayLarge
            )

            Text(
                weather.condition,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(
                Modifier.height(12.dp)
            )

            Text(
                "Feels like ${weather.feelsLike.toInt()}°"
            )

            WeatherOutlineButton(
                onClick = onToggleFavorite,
                modifier = Modifier.padding(top = 12.dp),
                text = if (isFavorite) "Remove from favorites" else "Save to favorites"
            )
        }
    }
}
