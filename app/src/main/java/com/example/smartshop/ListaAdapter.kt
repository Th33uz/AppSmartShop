package com.example.smartshop

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.smartshop.databinding.ItemListaBinding

class ListaAdapter(private var listas: MutableList<Lista>) :
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
                Intent(ctx, ItensActivity::class.java)
                    .putExtra("titulolista", lista.titulo)
            )
        }

        b.root.setOnLongClickListener {
            val ctx = b.root.context
            val opcoes = arrayOf("Editar", "Excluir")

            AlertDialog.Builder(ctx)
                .setTitle(lista.titulo)
                .setItems(opcoes) { _, which ->
                    when (which) {
                        0 -> {
                            val input = android.widget.EditText(ctx).apply {
                                setText(lista.titulo)
                                setSelection(text.length)
                            }
                            AlertDialog.Builder(ctx)
                                .setTitle("Editar lista")
                                .setView(input)
                                .setPositiveButton("Salvar") { _, _ ->
                                    val novoTitulo = input.text.toString().trim()
                                    if (novoTitulo.isNotEmpty()) {
                                        lista.titulo = novoTitulo
                                        notifyItemChanged(holder.bindingAdapterPosition)
                                    }
                                }
                                .setNegativeButton("Cancelar", null)
                                .show()
                        }
                        1 -> {
                            AlertDialog.Builder(ctx)
                                .setTitle("Excluir lista")
                                .setMessage("Excluir \"${lista.titulo}\" e todos os itens?")
                                .setPositiveButton("Excluir") { _, _ ->
                                    lista.itens.clear()
                                    ListaSession.listas.remove(lista)
                                    val idx = holder.bindingAdapterPosition
                                    if (idx != RecyclerView.NO_POSITION) {
                                        listas.removeAt(idx)
                                        notifyItemRemoved(idx)
                                    } else {
                                        listas = listas.filter { it != lista }.toMutableList()
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

    override fun getItemCount(): Int = listas.size

    fun updateList(newList: List<Lista>) {
        listas = newList.sortedBy { it.titulo }.toMutableList()
        notifyDataSetChanged()
    }
}
