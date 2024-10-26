package com.example.atividade.pages

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.atividade.WeatherPage
import com.example.atividade.WeatherViewModel


@Composable
fun HomePage(modifier: Modifier = Modifier, navController: NavController) {

    WeatherPage(viewModel = WeatherViewModel(), navController)

}