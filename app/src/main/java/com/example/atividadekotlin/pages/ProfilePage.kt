@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.atividade.pages

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.atividadekotlin.backend.AuthState
import com.example.atividadekotlin.backend.AuthViewModel
import com.example.atividadekotlin.backend.DAOViewModel
import com.example.atividadekotlin.backend.Localizacao
import com.example.atividadekotlin.backend.UserViewModel
import com.example.atividadekotlin.backend.Usuario
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProfilePage(modifier: Modifier = Modifier, navController: NavController, daoViewModel: DAOViewModel) {


    /*Column (
        modifier = modifier.fillMaxSize().background(Color.White),
        verticalArrangement = Arrangement.Center,//centralizar
        horizontalAlignment = Alignment.CenterHorizontally//centraliza na horizontal
    )
    {
        Text(
            text = "Perfil",
            fontSize = 40.sp,//tamanho do texto
            fontWeight = FontWeight.SemiBold,//texto em negrito
            color = Color.Blue//cor do texto
        )
    }*/

    var localizacoes by remember { mutableStateOf<List<Localizacao>>(emptyList()) }//lista de usuarios
    var usuario by remember { mutableStateOf<List<Usuario>>(emptyList()) }//lista de usuarios
    val context = LocalContext.current
    var nome: String by remember { mutableStateOf("") }
    var email: String by remember { mutableStateOf("") }
    var telefone: String by remember { mutableStateOf("") }
    var local: String by remember { mutableStateOf("") }
    val authViewModel: AuthViewModel = viewModel()
    val authState = authViewModel.authState.observeAsState()
    var isexpanded by remember {
        mutableStateOf(false)
    }

    val userViewModel: UserViewModel = viewModel()

    val auth: FirebaseAuth = FirebaseAuth.getInstance()

    val emailUsuario = auth.currentUser?.email?:""


    LaunchedEffect(authState.value) {
        when(authState.value){
            is AuthState.Logout -> navController.navigate("login")
            else -> {
                daoViewModel.listarUsuario(context = context)
                { data ->
                    nome = data.nome
                    telefone = data.telefone
                }
                val emailUsuario = auth.currentUser?.email

                daoViewModel.listarPrincipal()
                { data ->
                    if (data != null) {
                        local = data.descricao
                    }
                }
                daoViewModel.listar(context = context) { localizacoesList ->
                    localizacoes = localizacoesList
                }
            }
        }
    }

    Surface(color = MaterialTheme.colorScheme.background) {


        Column(
            modifier = Modifier
                .padding(30.dp)
                .fillMaxWidth()
                .wrapContentHeight(), horizontalAlignment = Alignment.CenterHorizontally
        ) {//centraliza
            Icon(
                modifier = Modifier
                    .width(120.dp)
                    .height(120.dp),//tamanho do icone
                imageVector = Icons.Default.AccountCircle, contentDescription = "Icon", tint = Color(0xFFE64A19) // Define a cor do ícone como laranja

            )//icone

            Spacer(modifier = Modifier.height(20.dp))

            val context = LocalContext.current

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            )
            {


                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(16.dp)), // Fundo whitesmoke
                    value = nome,
                    onValueChange = { nome = it },
                    label = { Text(text = "Nome", color = Color.Black) },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        focusedIndicatorColor = Color(0xFFE64A19), // Cor do indicador focado alterada para laranja escuro
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { Log.d("ImeAction", "Done pressed") }
                    ),
                    maxLines = 1,
                    singleLine = true,
                    textStyle = TextStyle(fontSize = 18.sp)
                )

                Spacer(modifier = Modifier.height(30.dp))

                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(16.dp)),
                    value = emailUsuario,
                    onValueChange = { email = it },
                    label = { Text(text = "Email", color = Color.Black) },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        focusedIndicatorColor = Color(0xFFE64A19),
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { Log.d("ImeAction", "Done pressed") }
                    ),
                    maxLines = 1,
                    singleLine = true,
                    textStyle = TextStyle(fontSize = 18.sp)
                )

                Spacer(modifier = Modifier.height(30.dp))

                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(16.dp)),
                    value = telefone,
                    onValueChange = { telefone = it },
                    label = { Text(text = "Telefone", color = Color.Black,fontSize = 16.sp) },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        focusedIndicatorColor = Color(0xFFE64A19),
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { Log.d("ImeAction", "Done pressed") }
                    ),
                    maxLines = 1,
                    singleLine = true,
                    textStyle = TextStyle(fontSize = 18.sp)
                )

                Spacer(modifier = Modifier.height(30.dp))

                ExposedDropdownMenuBox(
                    expanded = isexpanded,
                    onExpandedChange = { isexpanded = it }
                ) {
                    TextField(
                        value = local,
                        onValueChange = {
                            local = it
                        },
                        label = {
                            Text(text = "Localização Principal")
                        },
                        trailingIcon = {
                            IconButton(onClick = {}) {
                                Icon(
                                    Icons.Filled.ArrowDropDown,
                                    contentDescription = "Editar"
                                ) // ícone no final do text
                            }
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Search
                        ),
                        keyboardActions = KeyboardActions(
                            onSearch = {
                                Log.d("ImeAction", "ipiiopoi") // mostra mensagem ao clicar no botão
                            }
                        ),
                        colors = ExposedDropdownMenuDefaults.textFieldColors(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(16.dp))
                            .menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = isexpanded,
                        onDismissRequest = { isexpanded = false },
                        modifier = Modifier
                            .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(16.dp)) //
                    ) {
                        localizacoes.forEach { localizacao ->
                            DropdownMenuItem(
                                text = { Text(text = localizacao.descricao ?: "Sem descrição") },
                                onClick = {
                                    local = localizacao.descricao ?: "Sem descrição"
                                    isexpanded = false // Fecha o menu após selecionar
                                }
                            )
                        }
                    }
                }


                Spacer(modifier = Modifier.padding(20.dp))

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp),
                    onClick = {
                        var userData = Usuario(
                            nome = nome,
                            telefone = telefone,
                        )
                        var localizacaoData = Localizacao(
                            descricao = local,
                        )


                        daoViewModel.editarUsuario(usuario = userData, context = context) { result,error ->
                            if (result) {
                                userViewModel.editarUsuario(nome = nome, telefone = telefone, email = emailUsuario)
                                Toast.makeText(context, "Usuário editado com sucesso", Toast.LENGTH_SHORT).show()

                                daoViewModel.editarLocal(localizacao = localizacaoData, context = context) { result, error ->
                                    if (result) {
                                        navController.navigate("menu")
                                    } else {
                                        Toast.makeText(context, error ?: "Erro ao editar local", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            } else {
                                Toast.makeText(context, error ?: "Erro ao editar usuário", Toast.LENGTH_SHORT).show()
                            }
                        }

                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE64A19)), // Cor do botão alterada para laranja escuro
                    shape = RoundedCornerShape(12.dp),
                    elevation = ButtonDefaults.elevatedButtonElevation(4.dp)
                ) {
                    Text(text = "Editar", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold) // Fonte maior e negrito
                }

                Spacer(modifier = Modifier.padding(10.dp))

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp),
                    onClick = {

                        authViewModel.logout()
                        navController.navigate("login") {
                            popUpTo(0)
                        }

                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE64A19)), // Cor do botão alterada para laranja escuro
                    shape = RoundedCornerShape(12.dp),
                    elevation = ButtonDefaults.elevatedButtonElevation(4.dp)
                ) {
                    Text(text = "Sair", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold) // Fonte maior e negrito
                }
            }

        }

    }
    }








