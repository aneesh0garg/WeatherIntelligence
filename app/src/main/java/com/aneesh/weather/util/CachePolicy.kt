package com.aneesh.weather.util

object CachePolicy {

    const val WEATHER_CACHE_TTL = 30 * 60 * 1000L

    /** Kept pure so cache policy remains straightforward to unit test. */
    fun isStale(updatedAt: Long, now: Long = System.currentTimeMillis()): Boolean =
        now - updatedAt > WEATHER_CACHE_TTL

}
