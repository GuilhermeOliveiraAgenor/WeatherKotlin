package com.example.atividadekotlin.pages


import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.atividadekotlin.backend.AuthState
import com.example.atividadekotlin.backend.AuthViewModel
import com.example.atividadekotlin.backend.UserViewModel
import com.example.atividadekotlin.helper.ConnectionStatus
import com.example.atividadekotlin.helper.currentConnectivityStatus
import com.example.atividadekotlin.helper.observeConnectivityasFlow
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.runtime.State
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun connectivityStatus(): State<ConnectionStatus> {

    val mCTX = LocalContext.current

    // Cria e retorna um State de tipo ConnectionStatus
    return produceState(initialValue = mCTX.currentConnectivityStatus) {

        // Colete o fluxo de conectividade
        mCTX.observeConnectivityasFlow().collect { status ->
            value = status // Atualiza o estado com o novo status de conectividade
        }
    }
}

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun Login(navController: NavController, authViewModel :AuthViewModel) {

    val connection by connectivityStatus()

    val isConnected = connection === ConnectionStatus.Conectado


    val authState = authViewModel.authState.observeAsState()
    var email: String by remember { mutableStateOf("") }
    var senha: String by remember { mutableStateOf("") }
    val context = LocalContext.current
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    val userViewModel: UserViewModel = viewModel()

    LaunchedEffect(authState.value) {

        authViewModel.checkAuthStatus()

        when(authState.value){
            AuthState.Autenticado -> navController.navigate("menu")
            else -> Unit
        }

    }

    Column(
        Modifier
            .padding(24.dp)
            .fillMaxSize(), verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Icon(
            modifier = Modifier
                .width(120.dp)
                .height(120.dp),//tamanho do icone
            imageVector = Icons.Default.AccountCircle, contentDescription = "Icon"
        )//icone

        Spacer(modifier = Modifier.padding(5.dp))

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(16.dp)), // Fundo whitesmoke
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Email", color = Color.Black) },
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
            textStyle = TextStyle(fontSize = 18.sp) // Aumentando o tamanho da fonte do texto
        )

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(16.dp)), // Fundo whitesmoke
            value = senha,
            onValueChange = { senha = it },
            label = { Text(text = "Senha", color = Color.Black) },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedIndicatorColor = Color(0xFFE64A19),
                unfocusedIndicatorColor = Color.Transparent,
            ),
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(fontSize = 18.sp),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.padding(5.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            onClick = {
                if(isConnected){
                    Log.i("iuiiu","Conectado")
                    authViewModel.login(email,senha){ result, error ->
                        if(result){
                            navController.navigate("menu")
                        }
                        else{
                            Toast.makeText(context, error ?: "Erro ao fazer login", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                else{
                    Log.i("iuiiu","Desconectado")
                    userViewModel.loginUsuario(email, senha){ result ->
                        result.onSuccess { userList ->
                            navController.navigate("listar/$email")
                        }.onFailure { exception ->
                            Toast.makeText(context, "Erro ao fazer login", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE64A19)),
            shape = RoundedCornerShape(12.dp),
            elevation = ButtonDefaults.elevatedButtonElevation(4.dp)
        ) {
            Text(text = "Login", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }


        HorizontalDivider(
            color = Color.White.copy(alpha = 0.3f),
            thickness = 1.dp,
            modifier = Modifier.padding(top = 48.dp)
        )
        Row(verticalAlignment = Alignment.CenterVertically){
            Text("Não tem cadastro ?", fontSize = 20.sp, color = Color.Black,fontWeight = FontWeight.Bold)
            TextButton(onClick = {

                authViewModel.logout()
                navController.navigate("cadastrar") {
                    popUpTo(0) // Remove todas as telas anteriores da pilha de navegação
                }               }) {
                Text(
                    text = "Clique aqui", color = Color(0xFFE64A19), fontSize = 20.sp, fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

sealed class Texto(
    val label: String,
    val icon: ImageVector,
    val keyboardActions: KeyboardOptions,
    val visualTransformation: VisualTransformation)
{
    object Nome: Texto(label = "Usuário", icon = Icons.Default.Person, KeyboardOptions(imeAction = ImeAction.Next), visualTransformation = VisualTransformation.None)

    object Senha: Texto(label = "Senha", icon = Icons.Default.Lock, KeyboardOptions(imeAction = ImeAction.Next), visualTransformation = VisualTransformation.None)

}

@Composable
fun TextInput(text: Texto){
    var value by remember{
        mutableStateOf("")
    }

    TextField(value = value, onValueChange = { value = it}, modifier = Modifier.fillMaxWidth(), leadingIcon = { Icon(imageVector = text.icon, null) },
        label = { Text(text = text.label)})

}

