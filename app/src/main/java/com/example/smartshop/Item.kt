package com.example.smartshop

data class Item(
    var nome: String,
    var quantidade: Int,
    val unidade: String,
    val categoria: String,
    var comprado: Boolean = false
)
