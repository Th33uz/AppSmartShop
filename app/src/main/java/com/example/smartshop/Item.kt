package com.example.smartshop

data class Item(
    val nome: String,
    val quantidade: Int,
    val unidade: String,
    val categoria: String,
    var comprado: Boolean = false
)
