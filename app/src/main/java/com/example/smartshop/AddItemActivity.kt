package com.example.smartshop

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddItemActivity : AppCompatActivity() {
    private lateinit var inputNomeItem: EditText
    private lateinit var inputQuantidade: EditText
    private lateinit var spinnerUnidade: Spinner
    private lateinit var spinnerCategoria: Spinner
    private lateinit var btnAdicionarItem: Button

    private var lista: Lista? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        inputNomeItem = findViewById(R.id.inputNomeItem)
        inputQuantidade = findViewById(R.id.inputQuantidade)
        spinnerUnidade = findViewById(R.id.spinnerUnidade)
        spinnerCategoria = findViewById(R.id.spinnerCategoria)
        btnAdicionarItem = findViewById(R.id.btnAdicionarItem)

        val tituloLista = intent.getStringExtra("titulolista")
        lista = ListaSession.listas.find { it.titulo == tituloLista }

        if (lista == null) {
            Toast.makeText(this, "Lista não encontrada", Toast.LENGTH_SHORT).show()
            finish()
            return
        }


        val unidades = listOf("un", "kg", "g", "L")
        spinnerUnidade.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            unidades
        )

        val categorias = listOf("Fruta", "Verdura", "Carne", "Chocolate", "Pão", "Bebida", "Limpeza", "Outros")
        spinnerCategoria.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            categorias
        )


        btnAdicionarItem.setOnClickListener {
            val nome = inputNomeItem.text.toString().trim()
            val quantidadeTxt = inputQuantidade.text.toString().trim()
            val unidade = spinnerUnidade.selectedItem.toString()
            val categoria = spinnerCategoria.selectedItem.toString()

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
