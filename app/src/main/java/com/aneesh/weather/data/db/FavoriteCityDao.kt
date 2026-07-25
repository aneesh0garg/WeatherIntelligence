package com.aneesh.weather.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteCityDao {
    @Query("SELECT * FROM favorite_cities ORDER BY addedAt DESC")
    fun observeAll(): Flow<List<FavoriteCityEntity>>

    @Query("SELECT city FROM favorite_cities ORDER BY addedAt DESC")
    suspend fun getCities(): List<String>

    @Query("SELECT city FROM favorite_cities ORDER BY lastSelectedAt DESC, addedAt DESC LIMIT 1")
    suspend fun getLastSelectedCity(): String?

    @Query("UPDATE favorite_cities SET lastSelectedAt = :selectedAt WHERE lower(city) = lower(:city)")
    suspend fun markSelected(city: String, selectedAt: Long)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_cities WHERE city = :city)")
    fun isFavorite(city: String): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(city: FavoriteCityEntity)

    @Delete
    suspend fun delete(city: FavoriteCityEntity)
}
