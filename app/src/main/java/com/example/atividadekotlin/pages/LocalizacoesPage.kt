package com.example.atividade.pages

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.atividadekotlin.backend.AuthState
import com.example.atividadekotlin.backend.AuthViewModel
import com.example.atividadekotlin.backend.DAOViewModel
import com.example.atividadekotlin.backend.Localizacao
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LocalizacoesPagePreview(modifier: Modifier = Modifier, navController: NavController, daoViewModel: DAOViewModel) {

    var localizacoes by remember { mutableStateOf<List<Localizacao>>(emptyList()) }//lista de usuarios
    val context = LocalContext.current
    val auth : FirebaseAuth = FirebaseAuth.getInstance()
    val authViewModel: AuthViewModel = viewModel()
    val authState = authViewModel.authState.observeAsState()

    LaunchedEffect(authState.value) {
        when(authState.value){
            is AuthState.Logout -> navController.navigate("login")
            else -> {
                daoViewModel.listar(context = context) { localizacoesList ->
                    localizacoes = localizacoesList
                }
            }
        }
    }


    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth()
            .wrapContentHeight(), horizontalAlignment = Alignment.CenterHorizontally
    ) {//centraliza
        Icon(
            modifier = Modifier
                .width(80.dp)
                .height(80.dp),//tamanho do icone
            imageVector = Icons.Default.LocationOn,
            contentDescription = "Icon",
            tint = Color(0xFFE64A19)

        )//icone

        Spacer(modifier = Modifier.padding(5.dp))

        Text(
            text = "Localizações",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            ),
            color = Color.Black
        )

    }
    LazyColumn {
        items(localizacoes) { localizacao ->

            Card(
                modifier = modifier
                    .padding(15.dp)
                    .border(2.dp, Color(0xFFE64A19), shape = RoundedCornerShape(16.dp)), // Borda arredondada laranja
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 12.dp
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp).height(22.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = localizacao.descricao,
                        fontSize = 20.sp,
                        color = Color.Black,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = {
                        daoViewModel.excluirLocal(localizacao.descricao, context){ result, error ->
                            if(result){
                                Toast.makeText(context, "Localização excluída com sucesso", Toast.LENGTH_LONG).show()
                                navController.navigate("localizacoes")
                            }
                            else{
                                Toast.makeText(context, error, Toast.LENGTH_LONG).show()
                            }
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Excluir Localização",
                            tint = Color.Red,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
                }
            }
        }
    }


@Composable
fun Localizacoes(modifier: Modifier = Modifier, navController: NavController, daoViewModel: DAOViewModel) {
    Column (modifier = Modifier.fillMaxSize()){
        LocalizacoesPagePreview(navController = navController, daoViewModel = daoViewModel)
    }

}
