package com.example.atividade.api

sealed class NetworkResponse <out T>{//chama a classe WeatherModel

    data class Sucess<out T>(val data : T) : NetworkResponse<T>()
    data class Error(val message: String) : NetworkResponse<Nothing>()
    object Loading : NetworkResponse<Nothing>()

}