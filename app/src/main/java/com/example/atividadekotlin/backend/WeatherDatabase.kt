package com.example.atividadekotlin.backend

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = [User::class], version = 1)
@TypeConverters(Converters::class)
abstract class WeatherDatabase : RoomDatabase() {

    companion object{
        const val NAME = "weather"
    }

    abstract fun getWeatherDao() : userDAO

}