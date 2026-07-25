package com.aneesh.weather.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.aneesh.weather.R

data class PressFeedback(
    val interactionSource: MutableInteractionSource,
    val containerColor: Color
)

@Composable
fun rememberPressFeedback(
    defaultColor: Color = Color.Transparent,
    pressedColor: Color
): PressFeedback {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed = interactionSource.collectIsPressedAsState().value
    val containerColor = animateColorAsState(
        targetValue = if (isPressed) pressedColor else defaultColor,
        label = stringResource(R.string.pressfeedback)
    ).value
    return PressFeedback(interactionSource, containerColor)
}
