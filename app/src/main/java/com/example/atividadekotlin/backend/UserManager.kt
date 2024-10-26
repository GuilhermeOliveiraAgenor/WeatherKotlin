package com.example.atividadekotlin.backend

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.util.Date

object UserManager {

    private val userList = mutableListOf<User>()

    fun loginUsuario(email: String, senha: String) : List<User>{
        return userList.filter { it.email == email && it.senha == senha}
    }

    fun listarUsuario(email: String) : List<User>{
        return userList.filter { it.email == email}
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun adicionarUsuario(nome: String, telefone: String, email: String, senha: String, local: String){
        userList.add(User(System.currentTimeMillis().toInt(), nome, telefone, email,senha,local, Date.from(Instant.now())))
    }


}