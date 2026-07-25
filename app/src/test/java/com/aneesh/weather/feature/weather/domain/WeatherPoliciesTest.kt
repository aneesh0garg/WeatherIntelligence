package com.aneesh.weather.domain

import com.aneesh.weather.presentation.components.shouldShowRainChance
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class WeatherPoliciesTest {
    @Test
    fun `startup city priority is current then selected then recent then default`() {
        assertEquals("Mumbai", StartupCityResolver.resolve("Mumbai", "Paris", "London", "London"))
        assertEquals("Paris", StartupCityResolver.resolve(null, "Paris", "London", "London"))
        assertEquals("London", StartupCityResolver.resolve(null, null, "London", "Delhi"))
        assertEquals("Delhi", StartupCityResolver.resolve(null, null, null, "Delhi"))
    }

    @Test
    fun `only severe and extreme alerts are eligible for notification`() {
        assertTrue(SevereAlertPolicy.shouldNotify("Severe"))
        assertTrue(SevereAlertPolicy.shouldNotify("Extreme"))
        assertFalse(SevereAlertPolicy.shouldNotify("Moderate"))
    }

    @Test
    fun `rain chance is shown only above zero`() {
        assertFalse(shouldShowRainChance(0))
        assertTrue(shouldShowRainChance(1))
    }
}
