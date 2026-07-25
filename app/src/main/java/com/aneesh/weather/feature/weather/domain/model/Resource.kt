package com.aneesh.weather.feature.weather.domain.model

sealed interface Resource<out T> {

    data object Loading : Resource<Nothing>

    data class Success<T>(
        val data: T,
        /** True when a network refresh failed and the value is served from Room. */
        val isStale: Boolean = false
    ) : Resource<T>

    data class Error(
        val message: String
    ) : Resource<Nothing>

}
