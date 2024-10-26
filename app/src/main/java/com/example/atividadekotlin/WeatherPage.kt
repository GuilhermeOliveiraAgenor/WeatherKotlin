package com.example.atividade

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.atividade.api.NetworkResponse
import com.example.atividade.api.WeatherModel
import com.example.atividadekotlin.backend.AuthState
import com.example.atividadekotlin.backend.AuthViewModel
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherPage(
    viewModel: WeatherViewModel,
    navController: NavController
)
{

    val weatherResult = viewModel.weatherResult.observeAsState()//dados ao vivo

    val keyboardController = LocalSoftwareKeyboardController.current

    val auth : FirebaseAuth = FirebaseAuth.getInstance()
    val authViewModel: AuthViewModel = viewModel()
    val authState = authViewModel.authState.observeAsState()

    var cidade by remember {
        mutableStateOf("")//salva o valor da variável
    }

    LaunchedEffect(Unit) {
        when(authState.value){
            is AuthState.Logout -> navController.navigate("login")
            else -> {
                viewModel.listarLocal()
            }
        }
    }

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),//tamanho do caixa de texto
        horizontalAlignment = Alignment.CenterHorizontally,//centraliza a caixa de texto
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            OutlinedTextField(
                value = cidade,
                onValueChange = { cidade = it },
                modifier = Modifier.weight(1f),
                label = {
                    Text(
                        text = "Pesquise o local",
                        fontSize = 18.sp,
                        color = Color.Black // Define a cor da label (opcional)
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color(0xFFF5F5F5) // Fundo como "whitesmoke"
                ),
                shape = RoundedCornerShape(16.dp), // Forma arredondada
                textStyle = LocalTextStyle.current.copy(fontSize = 18.sp, color = Color.Black) // Aumenta a fonte e define a cor do texto
            )


            IconButton(
                onClick = {
                    viewModel.getData(cidade)
                    keyboardController?.hide()
                },
                modifier = Modifier
                    .background(Color.White, shape = RoundedCornerShape(16.dp))
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Pesquisar",
                    tint = Color(0xFFE64A19), // Cor do ícone laranja
                    modifier = Modifier.size(40.dp) // Aumenta o tamanho do ícone (ajuste conforme necessário)

                )
            }

        }

        when(val result = weatherResult.value){
            is NetworkResponse.Error -> {
                Text(text = result.message,fontSize = 20.sp,fontWeight = FontWeight.Bold)
            }
            NetworkResponse.Loading -> {
                CircularProgressIndicator()//mostra o icone de carregando
            }
            is NetworkResponse.Sucess -> {
                //Text(text = result.data.toString())//mostra os dados
                listarClima(data = result.data)
            }
            null -> {}
        }

    }
}



@Composable
fun listarClima(data: WeatherModel){

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment  = Alignment.Bottom
        ){
            Icon(
                imageVector = Icons.Default.LocationOn,//icone de localizacao
                contentDescription = "Localização",
                modifier = Modifier.size(40.dp),
                tint = Color(0xFFE64A19)

            )
            Text(text = data.location.name, fontSize = 30.sp)//passa os atributos das classes
            Spacer(modifier = Modifier.width(8.dp))//espaço entre os dados
            Text(text = data.location.country, fontSize = 22.sp, color = Color.Black)

        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Bottom
        ) {
            Spacer(modifier = Modifier.width(45.dp)) // espaço à esquerda


            Text(
                fontSize = 22.sp,
                color = Color.Black,
                text = data.location.localtime.split(" ")[1] // horário
            )
        }

    }
    Spacer (modifier = Modifier.height(16.dp))
    Text(
        text = "${data.current.temp_c} ° c",//graus
        fontSize = 56.sp,//tamanho do texto
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center//centraliza o texto
    )

    AsyncImage(
        modifier = Modifier.size((130.dp)),//tamanho do icone
        model = "https:${data.current.condition.icon}".replace("64x64", "128x128"),//chama o icone da temperatura
        contentDescription = "Icone de temperatura"
    )

    Text(
        text = data.current.condition.text,//graus
        fontSize = 20.sp,//tamanho do texto
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,//centraliza o texto
        color = Color.Black
    )

    Spacer (modifier = Modifier.height(16.dp))
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(width = 3.dp, color = Color(0xFFE64A19), shape = RoundedCornerShape(16.dp)), // Bordas mais grossas e cor laranja claro
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)) // Define o fundo como "whitesmoke"
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp) // largura do card
        ) {
            Row( // ao adicionar linha os elementos estarão do lado do outro
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                listarDados(key = "Umidade", value = data.current.humidity + "%") // adiciona os dados
                listarDados(key = "Velocidade do vento", value = data.current.wind_kph + " km/h") // passa o atributo, adiciona os dados
            }
            Row( // se adicionar outro row, cria outra linha
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                listarDados(key = "UV(Radiação)", value = data.current.uv) // passa o atributo, adiciona os dados
                listarDados(key = "Chuva", value = data.current.precip_mm + " mm") // passa o atributo, adiciona os dados
            }
        }
    }

}


@Composable
fun listarDados(key : String, value: String){
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = value, fontSize = 24.sp, fontWeight = FontWeight.Bold) // passa os valores para a variável
        Text(text = key, fontWeight = FontWeight.SemiBold, color = Color(0xFFE64A19)) // Cor laranja pastel
    }

}
