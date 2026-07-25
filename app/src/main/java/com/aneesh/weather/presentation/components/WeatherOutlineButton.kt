package com.aneesh.weather.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aneesh.weather.presentation.theme.LocalWeatherPalette

@Composable
fun WeatherOutlineButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val palette = LocalWeatherPalette.current
    val pressFeedback = rememberPressFeedback(
        pressedColor = palette.content.copy(alpha = 0.16f)
    )
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        border = BorderStroke(1.5.dp, palette.content),
        interactionSource = pressFeedback.interactionSource,
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 2.dp,
            pressedElevation = 6.dp
        ),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = pressFeedback.containerColor,
            contentColor = palette.content
        )
    ) {
        Text(text)
    }
}
