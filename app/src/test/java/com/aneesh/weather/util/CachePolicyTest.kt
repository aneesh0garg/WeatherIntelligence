package com.aneesh.weather.util

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class CachePolicyTest {

    @Test
    fun `cache is fresh at the ttl boundary`() {
        val now = 1_000_000L

        assertFalse(CachePolicy.isStale(now - CachePolicy.WEATHER_CACHE_TTL, now))
    }

    @Test
    fun `cache is stale after the ttl`() {
        val now = 1_000_000L

        assertTrue(CachePolicy.isStale(now - CachePolicy.WEATHER_CACHE_TTL - 1, now))
    }
}
