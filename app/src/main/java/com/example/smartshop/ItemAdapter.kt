package com.example.smartshop

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.smartshop.databinding.ItemProdutoBinding
import java.text.Collator
import java.util.Locale

class ItemAdapter(
    private var itens: MutableList<Item>,
    private val onItemCheck: (Item) -> Unit
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(val binding: ItemProdutoBinding) : RecyclerView.ViewHolder(binding.root)

    private val collator = Collator.getInstance(Locale("pt", "BR")).apply {
        strength = Collator.PRIMARY
    }

    private fun resort() {
        itens = itens
            .sortedWith(
                compareBy<Item> { it.comprado }                 // não marcados primeiro
                    .thenComparator { a, b -> collator.compare(a.nome, b.nome) } // A–Z
            )
            .toMutableList()
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

    }

    override fun getItemCount(): Int = itens.size
}
