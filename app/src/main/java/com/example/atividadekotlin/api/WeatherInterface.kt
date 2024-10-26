package com.example.atividade.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherInterface {

    @GET("/v1/current.json")//rota que chama o get
    suspend fun getWeather(
        @Query("key") apiKey : String,//passa a chave que dá acesso na api
        @Query("q") cidade: String// passa a cidade
    ) : Response<WeatherModel>


}