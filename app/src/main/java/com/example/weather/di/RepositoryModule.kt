package com.example.weather.di

import com.example.weather.feature.weather.data.repository.WeatherRepositoryImpl
import com.example.weather.feature.weather.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRepository(
        impl: WeatherRepositoryImpl
    ): WeatherRepository

}