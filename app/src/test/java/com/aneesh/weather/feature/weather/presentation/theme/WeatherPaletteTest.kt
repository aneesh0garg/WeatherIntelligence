package com.aneesh.weather.presentation.theme

import androidx.compose.ui.graphics.Color
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class WeatherPaletteTest {

    @Test
    fun `clear condition maps to warm palette`() {
        val palette = "Clear".toWeatherPalette()

        assertEquals(Color(0xFFFFA84B), palette.background.first())
        assertEquals(palette.background.last(), palette.cardContainer)
    }

    @Test
    fun `rain condition maps to blue palette`() {
        val palette = "Light rain shower".toWeatherPalette()

        assertEquals(Color(0xFF1E5D8E), palette.background.first())
        assertEquals(Color(0xFF59C8FF), palette.rain)
    }

    @Test
    fun `storm palette differs from clear palette`() {
        assertNotEquals(
            "Clear".toWeatherPalette().background,
            "Thunderstorm".toWeatherPalette().background
        )
    }
}
