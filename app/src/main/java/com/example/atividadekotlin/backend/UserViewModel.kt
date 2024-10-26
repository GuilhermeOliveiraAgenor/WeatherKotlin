package com.example.atividadekotlin.backend

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.Date

class UserViewModel: ViewModel() {

    val userDAO = MainApplication.weatherDB.getWeatherDao()

    /*init {
        getAllUsers()
    }
*/

    @RequiresApi(Build.VERSION_CODES.O)
    fun cadastrarUsuario(nome: String, telefone: String, email: String, senha: String, local: String){

        viewModelScope.launch(Dispatchers.IO){
            userDAO.cadastrarUsuario(User(nome = nome, telefone = telefone, email = email, senha = senha, local = local, createdAt = Date.from(
                Instant.now())))
        }

    }

    /*fun loginUsuario(email: String, senha: String): LiveData<List<User>>{
        return userDAO.loginUsuario(email, senha)
    }*/

    fun loginUsuario(email: String, senha: String, callback: (Result<List<User>>) -> Unit) {
        val userLiveData = userDAO.loginUsuario(email,senha)

        userLiveData.observeForever { userList ->
            if (userList != null && userList.any { it.senha == senha }) {
                // Sucesso, a lista de usuários que corresponde ao email e senha foi encontrada
                callback(Result.success(userList.filter { it.senha == senha }))
            } else {
                callback(Result.failure(Exception("NErro ao fazer login")))
            }
        }
    }

    fun listarUsuario(email: String, callback: (Result<List<User>>) -> Unit) {
        val userLiveData = userDAO.listarUsuario(email)

        userLiveData.observeForever { userList ->
            if (userList != null) {
                callback(Result.success(userList))
            } else {
                callback(Result.failure(Exception("NErro ao fazer login")))
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun editarUsuario(nome: String, telefone: String, email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                userDAO.editarUsuario(nome, telefone, email)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }



}