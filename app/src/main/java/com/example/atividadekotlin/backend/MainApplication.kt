package com.example.atividadekotlin.backend

import android.app.Application
import androidx.room.Room

class MainApplication: Application() {

    companion object{
        lateinit var weatherDB: WeatherDatabase
    }

    override fun onCreate() {
        super.onCreate()
        weatherDB = Room.databaseBuilder(
            applicationContext,
            WeatherDatabase::class.java,
            WeatherDatabase.NAME
        ).build()
    }


}