package com.example.smartshop

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListaAdapter(private var listas: List<Lista>) :
    RecyclerView.Adapter<ListaAdapter.ListaViewHolder>() {

    class ListaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgLista: ImageView = itemView.findViewById(R.id.imgLista)
        val txtNomeLista: TextView = itemView.findViewById(R.id.txtNomeLista)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_lista, parent, false)
        return ListaViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListaViewHolder, position: Int) {
        val lista = listas[position]

        holder.txtNomeLista.text = lista.titulo

        if (!lista.imagemUri.isNullOrEmpty()) {
            holder.imgLista.setImageURI(Uri.parse(lista.imagemUri))
        } else {
            holder.imgLista.setImageResource(R.drawable.iconeimg)
        }

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, ItensActivity::class.java)
            intent.putExtra("titulolista", lista.titulo)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = listas.size

    fun updateList(newList: List<Lista>) {
        listas = newList.sortedBy { it.titulo }
        notifyDataSetChanged()
    }
}
