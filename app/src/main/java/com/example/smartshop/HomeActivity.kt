package com.example.smartshop

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.recyclerview.widget.RecyclerView
import com.example.smartshop.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {


    private lateinit var adapter: ListaAdapter
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        binding.BtnLogout.setOnClickListener {

            UserSession.usuarioLogado = null
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)

            finish()
        }

        val recyclerView = binding.recyclerListas
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        adapter = ListaAdapter(emptyList())
        recyclerView.adapter = adapter

        binding.fabHome.setOnClickListener() {
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
