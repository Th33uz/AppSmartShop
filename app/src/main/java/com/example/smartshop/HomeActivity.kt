package com.example.smartshop

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.recyclerview.widget.RecyclerView

class HomeActivity : AppCompatActivity() {


    private lateinit var adapter: ListaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerListas)
        recyclerView.layoutManager = GridLayoutManager(this, 2)



        adapter = ListaAdapter(emptyList())
        recyclerView.adapter = adapter

        val fabHome = findViewById<FloatingActionButton>(R.id.fabHome)
        fabHome.setOnClickListener {
            val intent = Intent(this, AddListaActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        val usuario = UserSession.usuarioLogado
        val minhasListas = ListaSession.listas.filter { it.dono == usuario?.email }
        adapter.updateList(minhasListas)
    }
}
