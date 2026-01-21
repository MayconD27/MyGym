package com.example.mygym.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mygym.R
import com.example.mygym.`class`.FichaTreino
import com.example.mygym.`class`.Treino

class TreinoAdapter(private var lista: List<Treino>, private val onItemClick: (Treino) -> Unit) : RecyclerView.Adapter<TreinoAdapter.TreinoViewHolder>() {

    // Cria o visual: Infla o seu XML do card
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TreinoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card_cat_treino, parent, false)
        return TreinoViewHolder(view)
    }
    class TreinoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitulo: TextView = view.findViewById(R.id.tv_workout_title)
        val tvStatus: TextView = view.findViewById(R.id.text_status)
        val tvProgress: TextView = view.findViewById(R.id.tv_workout_status)
        val imageBackground: ImageView = view.findViewById(R.id.img_background_muscle)

    }

    // Alimenta o visual: Pega os dados da lista e coloca nos TextViews
    override fun onBindViewHolder(holder: TreinoViewHolder, position: Int) {
        val treino = lista[position]
        holder.itemView.setOnClickListener {
            onItemClick(treino)
        }
        when(treino.icone){
            1 -> {
                holder.imageBackground.setImageResource(R.drawable.ic_peitoral)
            }
            2 -> {
                holder.imageBackground.setImageResource(R.drawable.ic_costas)
            }
            3 ->{
                holder.imageBackground.setImageResource(R.drawable.ic_pernas)
            }
            4 -> {
                holder.imageBackground.setImageResource(R.drawable.ic_braco)
            }
            else ->{
                holder.imageBackground.setImageResource(R.drawable.ic_peitoral)
            }
        }
        holder.tvTitulo.text = treino.nome
        holder.tvProgress.text = treino.andamento
        holder.tvStatus.text = treino.progresso
    }

    override fun getItemCount() = lista.size
    fun atualizarLista(novaLista: List<Treino>) {
        this.lista = novaLista // Atualiza a variável 'lista' definida lá no topo
        notifyDataSetChanged()
    }



}