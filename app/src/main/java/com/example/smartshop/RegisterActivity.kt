package com.example.smartshop

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.smartshop.databinding.ActivityRegisterBinding
import android.util.Patterns
class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegistrarFinal.setOnClickListener {
            val nome = binding.inputNome.text.toString()
            val email = binding.inputEmailRegister.text.toString()
            val senha = binding.inputSenhaRegister.text.toString()
            val confirmaSenha = binding.inputConfirmaSenha.text.toString()


            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || confirmaSenha.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (senha != confirmaSenha) {
                Toast.makeText(this, "As senhas não coincidem", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Digite um e-mail válido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (UserSession.usuarios.any { it.email == email }) {
                Toast.makeText(this, "Já existe um usuário com este e-mail", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
                // lembrando esse return @setonClickListener e para sair do click e voltar para onCreate
            }

            val novoUsuario = User(nome, email, senha)
            UserSession.usuarios.add(novoUsuario)

            Toast.makeText(this, "Usuário registrado com sucesso!", Toast.LENGTH_SHORT).show()


            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
