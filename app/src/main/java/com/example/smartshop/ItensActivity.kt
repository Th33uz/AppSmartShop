package com.example.smartshop

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartshop.databinding.ActivityItensBinding

class ItensActivity : AppCompatActivity() {

    private lateinit var binding: ActivityItensBinding
    private lateinit var adapter: ItemAdapter
    private lateinit var lista: Lista

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItensBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val tituloLista = intent.getStringExtra("titulolista")
        val encontrada = ListaSession.listas.find { it.titulo == tituloLista }
        if (encontrada == null) { finish(); return }
        lista = encontrada

        binding.txtTituloLista.text = lista.titulo

        adapter = ItemAdapter(lista.itens) {}
        binding.recyclerItens.layoutManager = LinearLayoutManager(this)
        binding.recyclerItens.adapter = adapter

        binding.fabAddItem.setOnClickListener {
            startActivity(
                Intent(this, AddItemActivity::class.java)
                    .putExtra("titulolista", lista.titulo)
            )
        }
    }

    override fun onResume() {
        super.onResume()
        adapter.updateList(lista.itens)
    }
}
