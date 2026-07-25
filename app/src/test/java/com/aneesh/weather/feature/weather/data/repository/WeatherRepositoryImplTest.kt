package com.aneesh.weather.feature.weather.data.repository

import app.cash.turbine.test
import com.aneesh.weather.feature.weather.data.api.WeatherApi
import com.aneesh.weather.feature.weather.data.db.FavoriteCityDao
import com.aneesh.weather.feature.weather.data.db.WeatherDao
import com.aneesh.weather.feature.weather.data.db.WeatherEntity
import com.aneesh.weather.feature.weather.domain.model.Resource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class WeatherRepositoryImplTest {
    @Test
    fun `fresh cached weather is emitted without an API request`() = runTest {
        val api = mockk<WeatherApi>()
        val dao = mockk<WeatherDao>()
        val favorites = mockk<FavoriteCityDao>()
        val entity = weatherEntity(updatedAt = System.currentTimeMillis())
        coEvery { dao.getWeather("London") } returns entity
        every { dao.observeWeather("London") } returns flowOf(entity)

        WeatherRepositoryImpl(api, dao, favorites).getWeather("London").test {
            assertTrue(awaitItem() is Resource.Loading)
            val result = awaitItem()
            assertTrue(result is Resource.Success)
            assertEquals("London", (result as Resource.Success).data.city)
            cancelAndIgnoreRemainingEvents()
        }

        coVerify(exactly = 0) { api.getForecast(any(), any(), any(), any(), any()) }
    }

    private fun weatherEntity(updatedAt: Long) = WeatherEntity(
        city = "London", country = "UK", temperature = 20.0, feelsLike = 20.0,
        humidity = 50, pressure = 1010.0, wind = 10.0, condition = "Clear", icon = "",
        hourlyForecast = emptyList(), dailyForecast = emptyList(), updatedAt = updatedAt,
        localTime = "2026-07-25 12:00"
    )
}
