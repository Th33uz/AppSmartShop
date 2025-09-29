package com.example.smartshop

import Lista
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AddListaActivity : AppCompatActivity() {

    private lateinit var inputNomeLista: EditText
    private lateinit var imgPreviewLista: ImageView
    private lateinit var btnAdicionarLista: Button
    private lateinit var fabSelecionarImagem: FloatingActionButton

    private var imagemSelecionada: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_lista)

        inputNomeLista = findViewById(R.id.inputNomeLista)
        imgPreviewLista = findViewById(R.id.imgPreviewLista)
        btnAdicionarLista = findViewById(R.id.btnAdicionarLista)
        fabSelecionarImagem = findViewById(R.id.fabSelecionarImagem)

        fabSelecionarImagem.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK).apply {
                type = "image/*"
            }
            startActivityForResult(intent, 100)
        }

        // Botão Adicionar → cria lista
        btnAdicionarLista.setOnClickListener {
            val nomeLista = inputNomeLista.text.toString().trim()

            if (nomeLista.isEmpty()) {
                Toast.makeText(this, "Digite o nome da lista", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val usuario = UserSession.usuarioLogado
            if (usuario != null) {
                val novaLista = Lista(
                    titulo = nomeLista,
                    dono = usuario.email,
                    imagemUri = imagemSelecionada?.toString()
                )
                ListaSession.listas.add(novaLista)

                Toast.makeText(this, "Lista adicionada!", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Nenhum usuário logado", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            imagemSelecionada = data?.data
            imgPreviewLista.setImageURI(imagemSelecionada)
        }
    }
}
