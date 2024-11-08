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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.atividadekotlin.backend.AuthViewModel
import com.example.atividadekotlin.backend.DAOViewModel
import com.example.atividadekotlin.backend.Localizacao
import com.example.atividadekotlin.backend.LocalizacaoPrincipal
import com.example.atividadekotlin.backend.UserViewModel
import com.example.atividadekotlin.backend.Usuario
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CadastroPage(modifier: Modifier = Modifier, navController: NavController, dAOViewModel: DAOViewModel, authViewModel: AuthViewModel) {

    var nome: String by remember { mutableStateOf("") }
    var telefone: String by remember { mutableStateOf("") }
    var email: String by remember { mutableStateOf("") }
    var senha: String by remember { mutableStateOf("") }
    var descricao: String by remember { mutableStateOf("") }
    val context = LocalContext.current
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    val userViewModel: UserViewModel = viewModel()

    Surface(color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier
                .padding(30.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Faça o seu cadastro !",
                fontSize = 30.sp,
                color = Color(0xFFE64A19)
            )

            Spacer(modifier = Modifier.padding(5.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
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

                Spacer(modifier = Modifier.padding(15.dp))

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

                Spacer(modifier = Modifier.padding(15.dp))

                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(16.dp)),
                    value = senha,
                    onValueChange = { senha = it },
                    label = { Text(text = "Senha", color = Color.Black) },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        focusedIndicatorColor = Color(0xFFE64A19), // Cor do indicador focado
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password, // Define o tipo como senha
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { Log.d("ImeAction", "Done pressed") }
                    ),
                    maxLines = 1,
                    singleLine = true,
                    textStyle = TextStyle(fontSize = 18.sp),
                    visualTransformation = PasswordVisualTransformation() // Transforma o texto em caracteres de senha
                )

                Spacer(modifier = Modifier.padding(15.dp))

                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(16.dp)),
                    value = descricao,
                    onValueChange = { descricao = it },
                    label = { Text(text = "Localização", color = Color.Black) },
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

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    onClick = {
                        authViewModel.cadastrarUsuario(email.trim(),senha){result, error ->
                            if(result){
                                var userData = Usuario(
                                    nome = nome,
                                    telefone = telefone,
                                )

                                var localizacaoData = Localizacao(
                                    descricao = descricao,
                                )

                                var localizacaoPrincipalData = LocalizacaoPrincipal(
                                    descricao = descricao,
                                    principal = "Ativo"
                                )

                                dAOViewModel.cadastrarUsuario(usuario = userData, localizacaoPrincipal = localizacaoPrincipalData, localizacao = localizacaoData, email = email.trim(), context = context){result, error ->
                                    if(result){
                                        Log.i("iiuiiu", auth.currentUser?.email!!)
                                        userViewModel.cadastrarUsuario(nome = nome, telefone = telefone, email = email.trim(), senha = senha, local = descricao)
                                        navController.navigate("localizacoes")
                                    }
                                    else{
                                        Toast.makeText(context, error?: "Erro ao fazer login", Toast.LENGTH_SHORT).show()
                                    }
                                }

                            }
                            else{
                                Toast.makeText(context, error ?: "Erro ao cadastrar usuário", Toast.LENGTH_SHORT).show()
                            }
                        }

                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE64A19)),
                    shape = RoundedCornerShape(16.dp),
                    elevation = ButtonDefaults.elevatedButtonElevation(4.dp)
                ) {
                    Text(text = "Cadastrar", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}



