package com.example.smartshop

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartshop.databinding.ActivityItensBinding

class ItensActivity : AppCompatActivity() {

    private lateinit var binding: ActivityItensBinding
    private lateinit var adapter: ItemAdapter
    private var lista: Lista? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItensBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tituloLista = intent.getStringExtra("titulolista")
        lista = ListaSession.listas.find { it.titulo == tituloLista }

        if (lista == null) {
            finish()
            return
        }

        binding.txtTituloLista.text = lista!!.titulo

        adapter = ItemAdapter(lista!!.itens) { item ->
        }
        binding.recyclerItens.layoutManager = LinearLayoutManager(this)
        binding.recyclerItens.adapter = adapter

        binding.fabAddItem.setOnClickListener {
            val intent = Intent(this, AddItemActivity::class.java)
            intent.putExtra("titulolista", lista!!.titulo) // passa o nome da lista
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        lista?.let {
            adapter.updateList(it.itens)
        }
    }
}
