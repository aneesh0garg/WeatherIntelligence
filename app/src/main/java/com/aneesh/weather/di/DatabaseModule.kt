package com.aneesh.weather.di

import android.content.Context
import androidx.room.Room
import com.aneesh.weather.feature.weather.data.db.WeatherDao
import com.aneesh.weather.feature.weather.data.db.WeatherDatabase
import com.aneesh.weather.feature.weather.data.db.FavoriteCityDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): WeatherDatabase {

        return Room.databaseBuilder(
            context,
            WeatherDatabase::class.java,
            "weather.db"
        )
            .addMigrations(WeatherDatabase.MIGRATION_1_2, WeatherDatabase.MIGRATION_2_3)
            .build()
    }

    @Provides
    @Singleton
    fun provideWeatherDao(
        database: WeatherDatabase
    ): WeatherDao {

        return database.weatherDao()

    }

    @Provides
    @Singleton
    fun provideFavoriteCityDao(database: WeatherDatabase): FavoriteCityDao =
        database.favoriteCityDao()

}
