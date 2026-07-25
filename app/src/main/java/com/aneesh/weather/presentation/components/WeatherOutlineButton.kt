package com.aneesh.weather.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.aneesh.weather.presentation.theme.LocalWeatherPalette
import com.aneesh.weather.presentation.theme.Dimens

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
        border = BorderStroke(Dimens.BorderWidth, palette.content),
        interactionSource = pressFeedback.interactionSource,
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = Dimens.ButtonElevation,
            pressedElevation = Dimens.PressedButtonElevation
        ),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = pressFeedback.containerColor,
            contentColor = palette.content
        )
    ) {
        Text(text)
    }
}
