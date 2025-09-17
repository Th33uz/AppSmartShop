package com.example.smartshop

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity

class AddItemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)


        val unidades = listOf("un", "kg", "g", "L")
        val spinnerUnidade = findViewById<android.widget.Spinner>(R.id.spinnerUnidade)
        spinnerUnidade.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            unidades
        )

        val categorias = listOf("Fruta", "Verdura", "Carne", "Outros")
        val spinnerCategoria = findViewById<android.widget.Spinner>(R.id.spinnerCategoria)
        spinnerCategoria.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            categorias
        )
    }
}
