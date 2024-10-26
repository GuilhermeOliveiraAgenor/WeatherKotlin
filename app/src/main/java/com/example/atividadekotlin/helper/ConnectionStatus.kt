package com.example.atividadekotlin.helper

sealed class ConnectionStatus {

    object Conectado: ConnectionStatus()
    object Desconectado: ConnectionStatus()

}