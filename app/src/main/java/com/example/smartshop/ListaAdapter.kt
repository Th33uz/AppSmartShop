package com.example.smartshop

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.smartshop.databinding.ItemListaBinding

class ListaAdapter(private var listas: List<Lista>) :
    RecyclerView.Adapter<ListaAdapter.ListaViewHolder>() {

    class ListaViewHolder(val binding: ItemListaBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaViewHolder {
        val binding = ItemListaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListaViewHolder, position: Int) {
        val lista = listas[position]
        val b = holder.binding

        b.txtNomeLista.text = lista.titulo

        if (!lista.imagemUri.isNullOrEmpty()) {
            b.imgLista.setImageURI(Uri.parse(lista.imagemUri))
        } else {
            b.imgLista.setImageResource(R.drawable.iconeimg)
        }

        b.root.setOnClickListener {
            val ctx = b.root.context
            ctx.startActivity(
                Intent(ctx, ItensActivity::class.java).putExtra("titulolista", lista.titulo)
            )
        }
    }

    override fun getItemCount(): Int = listas.size

    fun updateList(newList: List<Lista>) {
        listas = newList.sortedBy { it.titulo }
        notifyDataSetChanged()
    }
}
