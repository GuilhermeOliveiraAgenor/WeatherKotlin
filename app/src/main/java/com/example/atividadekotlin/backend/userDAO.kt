package com.example.atividadekotlin.backend

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface userDAO {

    @Query("SELECT *FROM USER WHERE email = :email and senha = :senha")
    fun loginUsuario(email: String, senha: String): LiveData<List<User>>

    @Query("SELECT *FROM USER WHERE email = :email")
    fun listarUsuario(email: String): LiveData<List<User>>

    @Insert
    fun cadastrarUsuario(user: User)

    @Query("UPDATE User SET nome = :nome, telefone = :telefone WHERE email = :email")
    suspend fun editarUsuario(nome: String, telefone: String, email: String)
}