package com.example.atividadekotlin.pages.nav

sealed class NavTela(val route: String) {

    object Cadastrar: NavTela(route = "cadastrar")
    object Login: NavTela(route = "login")
    object Menu: NavTela(route = "menu")
    object Perfil: NavTela(route = "perfil")
    object Localizacoes: NavTela(route = "localizacoes")
    object CadastrarLocal: NavTela(route = "local")
    object Weather: NavTela(route = "weather")
    object ListarUsuario: NavTela(route = "listar")





}