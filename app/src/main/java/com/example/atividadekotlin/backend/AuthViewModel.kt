package com.example.atividadekotlin.backend

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class AuthViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState


    init {
        checkAuthStatus()
    }

    fun checkAuthStatus(){
        Log.i("uiiuiu", _authState.value.toString())

        if(auth.currentUser == null){
            _authState.value = AuthState.Logout
        }
        else{
            _authState.value = AuthState.Autenticado
        }
        Log.i("uiiuiu", _authState.value.toString())

    }

    fun login(email: String, senha: String, callback: (Boolean, String?) -> Unit) {
        if (email.isEmpty() || senha.isEmpty()) {
            val error = "Erro ao fazer login: Email e senha obrigatórios"
            _authState.value = AuthState.Error("Erro ao fazer login")
            callback(false, error)
            return
        }

        _authState.value = AuthState.Carregando

        try {
            auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState.Autenticado
                    Log.i("Logado", "Usuário logado:")
                    callback(true,null)
                } else {
                    val error = task.exception?.message ?: "Erro ao fazer login"
                    _authState.value = AuthState.Error(error)
                    Log.i("Logado", "Erro ao fazer login")
                    callback(false,error)
                }
            }
        }
        catch (e: Exception){
            Log.e("Erro", "Erro ao fazer login")
        }


    }



    fun cadastrarUsuario(email: String, senha: String, callback: (Boolean, String?) -> Unit) {


        if (email.isEmpty() || senha.isEmpty()) {
            val error = "Erro ao fazer login: Email e senha obrigatórios"
            _authState.value = AuthState.Error(error)
            callback(false, error)
            return
        }

        _authState.value = AuthState.Carregando

        try {

            auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState.Carregando
                    auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener { signInTask ->
                        if (signInTask.isSuccessful) {
                            _authState.value = AuthState.Autenticado
                            callback(true, null)
                        } else {
                            val error = signInTask.exception?.message ?: "Erro ao fazer login"
                            _authState.value = AuthState.Error(error)
                            callback(false, error)
                        }
                    }
                } else {
                    val error = task.exception?.message ?: "Erro ao criar o usuário"
                    _authState.value = AuthState.Error(error)
                    callback(false, error)
                }
            }
        } catch (e: Exception) {
            val error = e.message ?: "Erro ao cadastrar usuário"
            Log.e("Erro", "Erro ao cadastrar usuário: $error")
            _authState.value = AuthState.Error(error)
            callback(false, error)
        }
    }

    fun logout(){
        auth.signOut()
        _authState.value = AuthState.Logout
    }

}

sealed class AuthState{
    object Autenticado : AuthState()
    object Logout : AuthState()
    object Carregando : AuthState()
    data class Error(val message : String) : AuthState()

}