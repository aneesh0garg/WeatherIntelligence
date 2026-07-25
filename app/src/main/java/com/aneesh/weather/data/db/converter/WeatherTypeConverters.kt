package com.aneesh.weather.data.db.converter

import androidx.room.TypeConverter
import com.aneesh.weather.domain.model.DailyWeather
import com.aneesh.weather.domain.model.HourlyWeather
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class WeatherTypeConverters {

    private val gson = Gson()

    @TypeConverter
    fun fromHourly(list: List<HourlyWeather>): String {

        return gson.toJson(list)
    }

    @TypeConverter
    fun toHourly(json: String): List<HourlyWeather> {

        val type =
            object : TypeToken<List<HourlyWeather>>() {}.type

        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun fromDaily(list: List<DailyWeather>): String {

        return gson.toJson(list)
    }

    @TypeConverter
    fun toDaily(json: String): List<DailyWeather> {

        val type =
            object : TypeToken<List<DailyWeather>>() {}.type

        return gson.fromJson(json, type)
    }

}