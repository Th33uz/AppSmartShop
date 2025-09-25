package com.example.smartshop

object UserSession {
    val usuarios = mutableListOf<User>(
        User("Dev", "dev@dev.com", "dev")
    )
    var usuarioLogado: User? = null
}

data class User(
    val nome: String,
    val email: String,
    val senha: String
)