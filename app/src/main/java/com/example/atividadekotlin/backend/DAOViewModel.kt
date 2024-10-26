package com.example.atividadekotlin.backend

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class DAOViewModel(): ViewModel(){

    private val auth : FirebaseAuth = FirebaseAuth.getInstance()

    private val _authState = MutableLiveData<AuthState>()
    val authState : LiveData<AuthState> = _authState

    fun cadastrarUsuario(
        usuario: Usuario,
        localizacaoPrincipal: LocalizacaoPrincipal,
        localizacao: Localizacao,
        context: Context,
        email: String,
        callback: (Boolean, String?) -> Unit
    ) = CoroutineScope(Dispatchers.IO).launch{

        var emailUsuario = auth.currentUser?.email

        val firebase = Firebase.firestore.collection("usuario").document(email)

        var FirebaseLocalizacaoPrincipal =
            Firebase.firestore.collection("usuario").document(email)
                .collection("localizacaoPrincipal").document(email)

        var firebaseLocalizacao =
            Firebase.firestore.collection("usuario").document(emailUsuario!!)
                .collection("localizacaoPrincipal").document(emailUsuario!!).collection("localizacao")
                .document(localizacao.descricao)

        try {
            firebase.set(usuario).addOnSuccessListener {

                Log.i("Usuário cadastrado", "Usuário cadastrado com sucesso")

                FirebaseLocalizacaoPrincipal.set(localizacaoPrincipal).addOnSuccessListener {

                    Log.i("Localizacao principal cadastrada", "Localizacao principal cadastrada com sucesso")

                    firebaseLocalizacao.set(localizacao).addOnSuccessListener {

                        Log.i("Localizacao cadastrada", "Localizacao cadastrada com sucesso")
                        callback(true, null)

                    }.addOnFailureListener { exception ->
                        Toast.makeText(context, "Erro ao listar localizações: ${exception.message}", Toast.LENGTH_LONG).show()
                        callback(false, exception.message)
                    }
                }
            }
        }
        catch (e: Exception){
            Log.e("Erro", "Erro ao cadastrar usuário")
        }

    }

    fun listarUsuario(
        context: Context,
        data: (Usuario) -> Unit
    ) = CoroutineScope(Dispatchers.IO).launch{
        var emailUsuario = auth.currentUser?.email

        val firebase = Firebase.firestore.collection("usuario").document(emailUsuario!!)

        try {
            firebase.get().addOnSuccessListener {//lista o usuario
                if(it.exists()){
                    Log.i("Usuário listado", "Usuário listado com sucesso")
                    val userData = it.toObject<Usuario>()!!
                    data(userData)
                    Log.i("Log", "Usuario: " + data(userData))

                }else{
                    Log.i("Usuário listado", "Erro ao listar usuário")
                }
            }
        }
        catch (e: Exception){
            Log.i("Erro", "Erro ao cadastrar usuário:  ${e.message}")
        }

    }

    fun listarPrincipal(
        data: (LocalizacaoPrincipal?) -> Unit
    ) = CoroutineScope(Dispatchers.IO).launch {
        var emailUsuario = auth.currentUser?.email

        var firebase =
            Firebase.firestore.collection("usuario").document(emailUsuario!!)
                .collection("localizacaoPrincipal");

        try {

            val result = firebase.get().await()

            if (!result.isEmpty) {
                val localizacaoPrincipal = result.toObjects(LocalizacaoPrincipal::class.java).firstOrNull()
                data(localizacaoPrincipal)
                Log.i("Localização listada", "Localização listada com sucesso")

            } else {
                Log.i("Localização listada", "Nenhuma localização ativa encontrada")
            }

        } catch (e: Exception) {
            Log.e("Erro", "Erro ao listar localização:  ${e.message}")
        }
    }
    /*
        public fun listarPrincipal(
            context: Context,
            email: String,
            data: (List<Localizacao>) -> Unit
        ) = CoroutineScope(Dispatchers.IO).launch{


            var firebase =
                Firebase.firestore.collection("usuario").document(email)
                    .collection("localizacao").whereEqualTo("principal","Ativo")



            try {
                val result = firebase.get().await()
                if(!result.isEmpty){
                    val localizacao = result.toObjects(Localizacao::class.java)//converte docuementos em objetos
                    data(localizacao)//retorna lista
                    Log.i("Localização listada", "Localização listada com sucesso")
                }
                else{
                    Log.i("Localização listada", "Erro ao listar Localização")
                }
            }
            catch (e: Exception){
                Log.i("Erro", "Erro ao listar localização")
            }

        }
    */
    public fun listar(
        context: Context,
        data: (List<Localizacao>) -> Unit,
    ) = CoroutineScope(Dispatchers.IO).launch{
        var emailUsuario = auth.currentUser?.email

        Log.i("uiiuiu", emailUsuario!!)

        var firebaseLocalizacao =
            Firebase.firestore.collection("usuario").document(emailUsuario!!)
                .collection("localizacaoPrincipal").document(emailUsuario!!).collection("localizacao")

        try {
            val result = firebaseLocalizacao.get().await()
            if(!result.isEmpty){
                val localizacoes = result.toObjects(Localizacao::class.java)//converte docuementos em objetos
                data(localizacoes)//retorna lista
                Log.i("Localizações listadas", "Localizacoes listadas com sucesso")

            }
            else{
                Log.i("Localizações listadas", "Erro ao listar localizacões")
            }
        }
        catch (e: Exception){
            Toast.makeText(context, "Erro ao listar localizações: ${e.message}", Toast.LENGTH_LONG).show()
        }

    }

    fun cadastrarLocal(
        localizacao: Localizacao,
        context: Context,
        callback: (Boolean, String?) -> Unit
    ) = CoroutineScope(Dispatchers.IO).launch{
        var emailUsuario = auth.currentUser?.email

        var FirebaseLocalizacaoPrincipal =
            Firebase.firestore.collection("usuario").document(emailUsuario!!)
                .collection("localizacaoPrincipal").document(emailUsuario!!).collection("localizacao")
                .document(localizacao.descricao)

        try {
            FirebaseLocalizacaoPrincipal.set(localizacao).addOnSuccessListener {
                Log.i("Localizacao cadastrada", "Localizacao cadastrada com sucesso")
                callback(true,null)
            }.addOnFailureListener { exception ->
                Toast.makeText(context, "Erro ao cadastrar localizações: ${exception.message}", Toast.LENGTH_LONG).show()
                callback(false, exception.message)
            }
        }
        catch (e: Exception){
            Log.e("Erro", "Erro ao cadastrar localizacao")
        }

    }

    fun editarUsuario(
        usuario: Usuario,
        context: Context,
        callback: (Boolean, String?) -> Unit
    ) = CoroutineScope(Dispatchers.IO).launch{
        var emailUsuario = auth.currentUser?.email

        val firebase = Firebase.firestore.collection("usuario").document(emailUsuario!!)

        try {
            firebase.set(usuario).addOnSuccessListener {
                Log.i("Usuário cadastrado", "Usuário cadastrado com sucesso")
                callback(true, null)
            }.addOnFailureListener { exception ->
                Toast.makeText(context, "Erro ao editar usuário: ${exception.message}", Toast.LENGTH_LONG).show()
                callback(false, exception.message)
            }

        }
        catch (e: Exception){
            Log.e("Erro", "Erro ao cadastrar usuário")
        }

    }

    fun editarLocal(
        localizacao: Localizacao,
        context: Context,
        callback: (Boolean, String?) -> Unit
    ) = CoroutineScope(Dispatchers.IO).launch{
        var emailUsuario = auth.currentUser?.email

        val firebase =
            Firebase.firestore.collection("usuario").document(emailUsuario!!)
                .collection("localizacaoPrincipal").document(emailUsuario!!)

        try {
            firebase.set(localizacao)
                .addOnSuccessListener {
                    Log.i("Localizacao alterada", "Localizacao alterada com sucesso")
                    callback(true,null)
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(context, "Erro ao editar localizações: ${exception.message}", Toast.LENGTH_LONG).show()
                    callback(false, exception.message)
                }
        } catch (e: Exception) {
            Log.e("Erro", "Erro ao alterar localizacao")
        }

    }

    fun excluirLocal(
        localizacao: String,
        context: Context,
        callback: (Boolean, String?) -> Unit
    ) = CoroutineScope(Dispatchers.IO).launch{
        var emailUsuario = auth.currentUser?.email

        val firebase =
            Firebase.firestore.collection("usuario").document(emailUsuario!!)
                .collection("localizacaoPrincipal").document(emailUsuario!!).collection("localizacao")
                .document(localizacao)

        try {
            firebase.delete()
                .addOnSuccessListener {
                    Log.i("Localizacao excluída", "Localizacao excluída com sucesso")
                    callback(true,null)
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(context, "Erro ao excluir: ${exception.message}", Toast.LENGTH_LONG).show()
                    callback(false, exception.message)
                }
        } catch (e: Exception) {
            Log.e("Erro", "Erro ao excluir localização")
            callback(false, e.message)
        }

    }




}