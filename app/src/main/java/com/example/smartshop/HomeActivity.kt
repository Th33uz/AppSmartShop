package com.example.smartshop

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.smartshop.databinding.ActivityHomeBinding
import androidx.recyclerview.widget.RecyclerView;

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var adapter: ListaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerListas.layoutManager = GridLayoutManager(this, 2)
        adapter = ListaAdapter(mutableListOf())
        binding.recyclerListas.adapter = adapter

        binding.fabHome.setOnClickListener {
            startActivity(Intent(this, AddListaActivity::class.java))
        }


        binding.BtnLogout.setOnClickListener {
            UserSession.usuarioLogado = null
            val i = Intent(this, MainActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(i)
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        val usuario = UserSession.usuarioLogado
        val minhasListas = ListaSession.listas.filter { it.dono == usuario?.email }
        adapter.updateList(minhasListas)
    }
}
