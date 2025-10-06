package com.example.smartshop

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.smartshop.databinding.ActivityAddListaBinding

class AddListaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddListaBinding
    private var imagemSelecionada: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddListaBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.fabSelecionarImagem.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK).apply { type = "image/*" }
            startActivityForResult(intent, 100)
        }


        binding.btnAdicionarLista.setOnClickListener {
            val nomeLista = binding.inputNomeLista.text.toString().trim()
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

    @Deprecated("startActivityForResult está deprecated; considere Activity Result API futuramente")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            imagemSelecionada = data?.data
            binding.imgPreviewLista.setImageURI(imagemSelecionada)
        }
    }
}
