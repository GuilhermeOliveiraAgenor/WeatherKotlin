package com.example.atividadekotlin.pages


import android.util.Log
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.atividadekotlin.backend.AuthViewModel
import com.example.atividadekotlin.backend.DAOViewModel
import com.example.atividadekotlin.backend.Localizacao
import com.example.atividadekotlin.backend.UserViewModel
import com.example.atividadekotlin.backend.Usuario

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListarUsuario(modifier: Modifier = Modifier, navController: NavController, daoViewModel: DAOViewModel) {


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

    val navBackStackEntry = navController.currentBackStackEntry
    val emailUsuario = navBackStackEntry?.arguments?.getString("email") ?: ""

    LaunchedEffect(Unit) {

        userViewModel.listarUsuario(emailUsuario) { result ->
            result.onSuccess { userList ->
                userList.forEach { user ->
                    nome = user.nome
                    email = user.email
                    telefone = user.telefone
                }
            }.onFailure { exception ->
                Log.e("Usuario", "Erro ao pesquisa usuário")
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
                imageVector = Icons.Default.AccountCircle, contentDescription = "Icon"
            )//icone

            Spacer(modifier = Modifier.padding(10.dp))

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
                    label = { Text(text = "Localização", color = Color.Black) },
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
                Spacer(modifier = Modifier.padding(20.dp))

                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(16.dp)),
                    value = email,
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

                Spacer(modifier = Modifier.padding(20.dp))
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(16.dp)),
                    value = telefone,
                    onValueChange = { telefone = it },
                    label = { Text(text = "Telefone", color = Color.Black) },
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

                Spacer(modifier = Modifier.padding(15.dp))

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    onClick = {
                        navController.navigate("login")
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE64A19)),
                    shape = RoundedCornerShape(12.dp),
                    elevation = ButtonDefaults.elevatedButtonElevation(4.dp)
                ) {
                    Text(text = "Voltar", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.padding(15.dp))

                Text(
                    text = "Você está Offline \nConecte na internet",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(), // Preenche a largura disponível
                    textAlign = TextAlign.Center // Centraliza o texto
                )

            }

        }

    }
}








