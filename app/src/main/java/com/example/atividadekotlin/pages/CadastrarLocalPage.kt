package com.example.atividadekotlin.pages


import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CadastrarLocal(navController: NavController, daoViewModel: DAOViewModel) {
    var descricao: String by remember { mutableStateOf("") }

    val context = LocalContext.current

    val authViewModel: AuthViewModel = viewModel()
    val authState = authViewModel.authState.observeAsState()

    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Logout -> navController.navigate("login")
            else -> Unit
        }
    }

    Column(
        Modifier
            .padding(24.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(24.dp, alignment = Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier
                .width(120.dp)
                .height(120.dp),
            imageVector = Icons.Default.LocationOn,
            contentDescription = "Icon",
            tint = Color(0xFFE64A19) // Cor do ícone alterada para um laranja mais escuro
        )

        // Título da tela
        Text(
            text = "Cadastrar Local",
            modifier = Modifier.padding(vertical = 16.dp),
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
        )

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(16.dp)), // Fundo whitesmoke
            value = descricao,
            onValueChange = { descricao = it },
            label = { Text(text = "Localização", color = Color.Black)},
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

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            onClick = {
                val localizacaoData = Localizacao(descricao = descricao)
                daoViewModel.cadastrarLocal(localizacao = localizacaoData, context = context) { result, error ->
                    if (result) {
                        navController.navigate("localizacoes")
                    } else {
                        Toast.makeText(context, error ?: "Erro ao cadastrar usuário", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE64A19)), // Cor do botão alterada para laranja escuro
            shape = RoundedCornerShape(12.dp),
            elevation = ButtonDefaults.elevatedButtonElevation(4.dp)
        ) {
            Text(text = "Cadastrar", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold) // Fonte maior e negrito
        }

    }
}
