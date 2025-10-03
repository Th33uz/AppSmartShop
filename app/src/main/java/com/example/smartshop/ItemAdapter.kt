package com.example.smartshop

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.smartshop.databinding.ItemProdutoBinding
import java.text.Collator
import java.util.Comparator
import java.util.Locale

class ItemAdapter(
    private var itens: MutableList<Item>,
    private val onItemCheck: (Item) -> Unit
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(val binding: ItemProdutoBinding) : RecyclerView.ViewHolder(binding.root)

    private val collator = Collator.getInstance(Locale("pt", "BR")).apply {
        strength = Collator.PRIMARY
    }

    private val categoriaOrder = listOf(
        "Fruta", "Verdura", "Carne", "Chocolate", "Pão", "Bebida", "Limpeza", "Outros"
    )
    private fun catRank(cat: String): Int {
        val i = categoriaOrder.indexOf(cat)
        return if (i == -1) Int.MAX_VALUE else i
    }

    private fun resort() {
        val comparator =
            compareBy<Item> { it.comprado }
                .thenBy { catRank(it.categoria) }
                .then(Comparator { a, b ->
                    collator.compare(a.nome, b.nome)
                })

        itens = itens.sortedWith(comparator).toMutableList()
        notifyDataSetChanged()
    }

    fun updateList(newItens: MutableList<Item>) {
        itens = newItens.toMutableList()
        resort()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemProdutoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = itens[position]
        val b = holder.binding

        b.txtNomeItem.text = item.nome
        b.txtQuantidade.text = "${item.quantidade} ${item.unidade}"

        when (item.categoria) {
            "Fruta"     -> b.imgCategoria.setImageResource(R.drawable.iconemaca)
            "Verdura"   -> b.imgCategoria.setImageResource(R.drawable.iconecenoura)
            "Carne"     -> b.imgCategoria.setImageResource(R.drawable.iconecarne)
            "Chocolate" -> b.imgCategoria.setImageResource(R.drawable.iconechocolate)
            "Pão"       -> b.imgCategoria.setImageResource(R.drawable.iconepao)
            "Bebida"    -> b.imgCategoria.setImageResource(R.drawable.iconegarrafa)
            "Limpeza"   -> b.imgCategoria.setImageResource(R.drawable.iconelimpeza)
            else        -> b.imgCategoria.setImageResource(R.drawable.iconeimg)
        }


        b.checkComprado.setOnCheckedChangeListener(null)
        b.checkComprado.isChecked = item.comprado
        b.checkComprado.setOnCheckedChangeListener { _, checked ->
            item.comprado = checked
            onItemCheck(item)
            resort()
        }

        b.root.setOnLongClickListener {
            val ctx = b.root.context
            val opcoes = arrayOf("Editar", "Excluir")

            androidx.appcompat.app.AlertDialog.Builder(ctx)
                .setTitle(item.nome)
                .setItems(opcoes) { _, which ->
                    when (which) {
                        0 -> {
                            val container = android.widget.LinearLayout(ctx).apply {
                                orientation = android.widget.LinearLayout.VERTICAL
                                setPadding(48, 24, 48, 0)
                            }
                            val inputNome = android.widget.EditText(ctx).apply {
                                hint = "Nome"
                                setText(item.nome)
                            }
                            val inputQtd = android.widget.EditText(ctx).apply {
                                hint = "Quantidade"
                                inputType = android.text.InputType.TYPE_CLASS_NUMBER
                                setText(item.quantidade.toString())
                            }
                            container.addView(inputNome)
                            container.addView(inputQtd)

                            androidx.appcompat.app.AlertDialog.Builder(ctx)
                                .setTitle("Editar item")
                                .setView(container)
                                .setPositiveButton("Salvar") { _, _ ->
                                    val novoNome = inputNome.text.toString().trim()
                                    val novaQtd = inputQtd.text.toString().toIntOrNull()
                                    if (novoNome.isNotEmpty()) item.nome = novoNome
                                    if (novaQtd != null) item.quantidade = novaQtd
                                    updateList(itens) // reordena e atualiza
                                }
                                .setNegativeButton("Cancelar", null)
                                .show()
                        }
                        1 -> {
                            androidx.appcompat.app.AlertDialog.Builder(ctx)
                                .setTitle("Excluir item")
                                .setMessage("Excluir \"${item.nome}\"?")
                                .setPositiveButton("Excluir") { _, _ ->
                                    val idx = holder.bindingAdapterPosition
                                    if (idx != RecyclerView.NO_POSITION) {
                                        itens.removeAt(idx)
                                        notifyItemRemoved(idx)
                                    } else {
                                        itens = itens.filter { it != item }.toMutableList()
                                        notifyDataSetChanged()
                                    }
                                }
                                .setNegativeButton("Cancelar", null)
                                .show()
                        }
                    }
                }
                .show()
            true
        }
    }

    override fun getItemCount(): Int = itens.size
}
