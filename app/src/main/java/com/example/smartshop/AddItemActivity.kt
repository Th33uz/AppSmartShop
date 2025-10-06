package com.example.smartshop

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.smartshop.databinding.ActivityAddItemBinding

class AddItemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddItemBinding
    private var lista: Lista? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tituloLista = intent.getStringExtra("titulolista")
        lista = ListaSession.listas.find { it.titulo == tituloLista }
        if (lista == null) {
            Toast.makeText(this, "Lista não encontrada", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val unidades = listOf("un", "kg", "g", "L")
        val categorias = listOf("Fruta", "Verdura", "Carne", "Chocolate", "Pão", "Bebida", "Limpeza", "Outros")

        binding.spinnerUnidade.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            unidades
        )
        binding.spinnerCategoria.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            categorias
        )

        binding.btnAdicionarItem.setOnClickListener {
            val nome = binding.inputNomeItem.text.toString().trim()
            val quantidadeTxt = binding.inputQuantidade.text.toString().trim()
            val unidade = binding.spinnerUnidade.selectedItem.toString()
            val categoria = binding.spinnerCategoria.selectedItem.toString()

            if (nome.isEmpty() || quantidadeTxt.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val quantidade = quantidadeTxt.toIntOrNull()
            if (quantidade == null) {
                Toast.makeText(this, "Quantidade inválida", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val novoItem = Item(
                nome = nome,
                quantidade = quantidade,
                unidade = unidade,
                categoria = categoria
            )

            lista?.itens?.add(novoItem)
            Toast.makeText(this, "Item adicionado!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
