package com.example.smartshop
data class Lista(
    var titulo: String,
    val dono: String,
    val imagemUri: String? = null,
    val itens: MutableList<Item> = mutableListOf()
)
