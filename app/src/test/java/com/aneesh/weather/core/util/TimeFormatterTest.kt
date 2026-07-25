package com.aneesh.weather.core.util

import com.aneesh.weather.util.toDisplayTime
import com.aneesh.weather.util.toForecastDayLabel
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TimeFormatterTest {
    private lateinit var previousLocale: Locale

    @Before
    fun useEnglishLocale() {
        previousLocale = Locale.getDefault()
        Locale.setDefault(Locale.US)
    }

    @After
    fun restoreLocale() {
        Locale.setDefault(previousLocale)
    }

    @Test
    fun `hourly time removes zero minutes`() {
        assertEquals("1 AM", "2026-07-25 01:00".toDisplayTime())
    }

    @Test
    fun `today date uses Today label`() {
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date())

        assertEquals("Today", today.toForecastDayLabel())
    }

    @Test
    fun `future date uses weekday name`() {
        assertEquals("Monday", "2026-07-27".toForecastDayLabel())
    }
}
