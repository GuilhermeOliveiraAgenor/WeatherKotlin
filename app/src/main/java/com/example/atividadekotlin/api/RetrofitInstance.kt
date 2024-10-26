package com.example.atividade.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val baseUrl = "https://api.weatherapi.com";//url de conexao

    private fun getInstance() : Retrofit{
        return Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build()//chama a api e converte o GSON
    }
    val weatherApi : WeatherInterface = getInstance().create(WeatherInterface::class.java)//cria a instancia para chamar a api



}