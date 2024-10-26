package com.example.atividade.ui.theme

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.atividade.pages.CadastroPage
import com.example.atividade.pages.HomePage
import com.example.atividade.pages.Localizacoes
import com.example.atividade.pages.ProfilePage
import com.example.atividadekotlin.backend.AuthViewModel
import com.example.atividadekotlin.backend.DAOViewModel
import com.example.atividadekotlin.pages.CadastrarLocal
import com.example.atividadekotlin.pages.ListarUsuario
import com.example.atividadekotlin.pages.Login
import com.example.atividadekotlin.pages.nav.NavTela

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(modifier: Modifier = Modifier, navController: NavHostController, daoViewModel: DAOViewModel, authViewModel: AuthViewModel) {
    val items = listOf(
        NavItem("Perfil", Icons.Default.AccountCircle, 1),
        NavItem("Menu", Icons.Default.Home, 0),
        NavItem("Localizações", Icons.Default.Place, 0),
        NavItem("Adicionar", Icons.Default.Add, 0)
    )

    var selectedIndex by remember { mutableIntStateOf(0) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, navItem ->
                    NavigationBarItem(
                        selected = selectedIndex == index,
                        onClick = {
                            selectedIndex = index
                            // Navegar para a rota correspondente no NavHost
                            when (index) {
                                0 -> navController.navigate(NavTela.Perfil.route)
                                1 -> navController.navigate(NavTela.Menu.route)
                                2 -> navController.navigate(NavTela.Localizacoes.route)
                                3 -> navController.navigate(NavTela.CadastrarLocal.route)
                            }
                        },
                        icon = {
                            BadgedBox(badge = {
                                if (navItem.badgeCount > 0) {
                                    Badge {
                                        Text(text = navItem.badgeCount.toString())
                                    }
                                }
                            }) {
                                Icon(
                                    imageVector = navItem.icon,
                                    contentDescription = "Icon",
                                    tint = if (selectedIndex == index) Color(0xFFE64A19) else Color.Black // Laranja se selecionado, preto caso contrário
                                )
                            }
                        },
                        label = {
                            Text(
                                text = navItem.label,
                                color = if (selectedIndex == index) Color(0xFFE64A19) else Color.Black, // Laranja se selecionado, preto caso contrário
                                fontWeight = FontWeight.Bold // Define o texto como negrito
                            )
                        }
                    )
                }
            }
        }
    )  { innerPadding ->
        // Conteúdo das telas, com padding aplicado
        Box(modifier = Modifier.padding(innerPadding)) {
            // NavHost contendo as rotas, aplicando o innerPadding nas telas
            NavHost(
                navController = navController,
                startDestination = NavTela.Login.route
            ) {
                composable(NavTela.Perfil.route) {
                    ProfilePage(navController = navController, daoViewModel = daoViewModel)
                }
                composable(NavTela.Menu.route) {
                    HomePage(navController = navController)
                }
                composable(NavTela.Localizacoes.route) {
                    Localizacoes(navController = navController, daoViewModel = daoViewModel)
                }
                composable(NavTela.CadastrarLocal.route) {
                    CadastrarLocal(navController = navController, daoViewModel = daoViewModel)
                }
                composable(NavTela.Login.route) {
                    Login(navController = navController, authViewModel = authViewModel)
                }
                composable(NavTela.Cadastrar.route) {
                    CadastroPage(navController = navController, dAOViewModel = daoViewModel, authViewModel = authViewModel)
                }
                composable(route = NavTela.ListarUsuario.route) {
                    ListarUsuario(navController = navController, daoViewModel = daoViewModel)
                }
                composable("listar/{email}") {
                    ListarUsuario(navController = navController, daoViewModel = daoViewModel)
                }
            }
        }
    }
}