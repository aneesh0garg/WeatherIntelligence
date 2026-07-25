package com.aneesh.weather.feature.weather.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.CardDefaults
import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.aneesh.weather.core.util.formatTemperature
import com.aneesh.weather.feature.weather.domain.model.Weather

@Composable
fun CurrentWeatherCard(
    weather: Weather,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = MaterialTheme.colorScheme.onSurface
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = containerColor,
            contentColor = contentColor
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

            OutlinedButton(
                onClick = onToggleFavorite,
                modifier = Modifier.padding(top = 12.dp),
                border = BorderStroke(1.dp, contentColor),
                colors = androidx.compose.material3.ButtonDefaults.outlinedButtonColors(contentColor = contentColor)
            ) {
                Text(if (isFavorite) "Remove from favorites" else "Save to favorites")
            }
        }
    }
}
