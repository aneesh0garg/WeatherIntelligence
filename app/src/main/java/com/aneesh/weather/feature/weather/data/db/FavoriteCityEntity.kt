package com.aneesh.weather.feature.weather.data.db

import androidx.room.Entity
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_cities")
data class FavoriteCityEntity(
    @PrimaryKey val city: String,
    val addedAt: Long = System.currentTimeMillis(),
    @ColumnInfo(defaultValue = "0") val lastSelectedAt: Long = 0
)
