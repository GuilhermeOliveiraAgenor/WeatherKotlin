package com.example.atividade

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.atividade.ui.theme.MainScreen
import com.example.atividadekotlin.backend.AuthViewModel
import com.example.atividadekotlin.backend.DAOViewModel


class MainActivity : ComponentActivity() {

    private lateinit var naveController: NavHostController
    private val dAOViewModel : DAOViewModel by viewModels()

    val authViewModel : AuthViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {

            naveController = rememberNavController()

            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                MainScreen(navController = naveController,daoViewModel = dAOViewModel, authViewModel = authViewModel)
            }
        }

    }
}

/*
@ExperimentalCoroutinesApi
@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun checkConnectivityStatus(){
    val connection by connectivityStatus()

    val isConnected = connection === ConnectionStatus.Conectado

    if(isConnected){
        Log.i("uiui","Conectado")
    }
    else{
        Log.i("uiui","Desconectado")
    }

}

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
*/

/*

data class Mensagem (val autor: String, val texto: String){}//classe de dados com atributos

@Composable
private fun Card(msg: Mensagem){//passa o objeto da classe

Row{
    Image(
        painter = painterResource(id = R.drawable.foto),//cria variavel, R.pasta.arquivo - adicionar imagens na pasta drawable
        contentDescription = "foto",//descrição da imagem
        modifier = Modifier
            .size(50.dp)
            .clip(CircleShape)//modifier.tamanho(digitar tamanho)
    )
    Column {
        Text(text = msg.autor);
        Text(text = msg.texto);
    }

}

    }

@Composable
fun texto(){
    Text("oiuoiuiouio", color = Color.Blue, fontStyle = FontStyle.Italic, fontWeight = FontWeight.Bold, fontSize = 60.sp);
}


}*/
/*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar() {

    var displayMenu by remember { mutableStateOf(false) }

    val context = LocalContext.current

    TopAppBar(
        title = { Text("Título") },
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Blue),//cor do menu

        actions = {

            IconButton(onClick = { /TODO/ }) {//cria o botão passa a funçao
                Icon(
                    Icons.Default.AccountCircle,
                    "perfil"
                )//Icons.Default. - chama os ícones na tela
            }

            IconButton(onClick = { displayMenu = !displayMenu }) {//cria o botão passa a funçao
                Icon(Icons.Default.Menu, "icone")//Icons.Default. - chama os ícones na tela
            }

            DropdownMenu(
                expanded = displayMenu,
                onDismissRequest = { displayMenu = false },
                modifier = Modifier.width((150.dp))
            ) {//largura do botão


                //item do menu
                DropdownMenuItem(text = { Text(text = "Login") },//texto do botao
                    onClick = {
                        Toast.makeText(context, "Login", Toast.LENGTH_SHORT).show()//mostra a mensagem do botao
                    })

                DropdownMenuItem(text = { Text(text = "Configuração") },//texto do botao
                    onClick = {
                        Toast.makeText(context, "Configuração", Toast.LENGTH_SHORT)
                    })

                DropdownMenuItem(text = { Text(text = "Perfil") },//texto do botao
                    onClick = {
                        Toast.makeText(context, "iuiouiouoi", Toast.LENGTH_SHORT)
                    })


            }

        }
    )
}*/




/*
@Preview
@Composable
private fun mostrarCard(){
    //Card(titulo = "\n\n\niouoiuoiuiouoiuoiuoiuiouiuioouiuoi")
}
*/