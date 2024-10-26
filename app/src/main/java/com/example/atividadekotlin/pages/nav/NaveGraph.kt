package com.example.atividadekotlin.pages.nav

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.atividade.pages.CadastroPage
import com.example.atividade.pages.Localizacoes
import com.example.atividade.pages.ProfilePage
import com.example.atividade.ui.theme.MainScreen
import com.example.atividadekotlin.backend.AuthViewModel
import com.example.atividadekotlin.backend.DAOViewModel
import com.example.atividadekotlin.pages.CadastrarLocal
import com.example.atividadekotlin.pages.ListarUsuario
import com.example.atividadekotlin.pages.Login
@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun naveGraph(navController: NavHostController, daoViewModel: DAOViewModel, authViewModel: AuthViewModel) {
    NavHost(navController = navController, startDestination = NavTela.Login.route) {
        composable(route = NavTela.Login.route) {
            Login(navController = navController, authViewModel = authViewModel)
        }
        composable(route = NavTela.Cadastrar.route) {
            CadastroPage(navController = navController, dAOViewModel = daoViewModel, authViewModel = authViewModel)
        }
        composable(route = NavTela.Menu.route) {
            MainScreen(navController = navController, daoViewModel = daoViewModel, authViewModel = authViewModel)
        }
        composable(route = NavTela.Perfil.route) {
            ProfilePage(navController = navController, daoViewModel = daoViewModel)
        }
        composable(route = NavTela.Localizacoes.route) {
            Localizacoes(navController = navController, daoViewModel = daoViewModel)
        }
        composable(route = NavTela.CadastrarLocal.route) {
            CadastrarLocal(navController = navController, daoViewModel = daoViewModel)
        }
        composable(route = NavTela.ListarUsuario.route) {
            ListarUsuario(navController = navController, daoViewModel = daoViewModel)
        }
    }
}