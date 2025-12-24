package com.example.mygym

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TreinoAdapter(private val lista: List<Treino>) : RecyclerView.Adapter<TreinoAdapter.TreinoViewHolder>() {

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
        val imageStatusStart : ImageView = view.findViewById(R.id.img_status_icon_finish)
        val imageStatusStop : ImageView = view.findViewById(R.id.img_status_icon_stop)
    }

    // Alimenta o visual: Pega os dados da lista e coloca nos TextViews
    override fun onBindViewHolder(holder: TreinoViewHolder, position: Int) {
        val treino = lista[position]
        holder.imageBackground.setImageResource(treino.icone)
        holder.tvTitulo.text = treino.nome
        holder.tvProgress.text = treino.andamento
        val progress = treino.progresso
        val partes = progress.split("/")
        val atual = partes[0].toInt()
        val total = partes[1].toInt()

        if (atual >= total) {
            holder.imageStatusStart.alpha = 1f
            holder.tvStatus.visibility = View.GONE
        }else if(atual == 0){
            holder.imageStatusStart.alpha = 0f
            holder.imageStatusStop.alpha = 1f
            holder.tvStatus.visibility = View.GONE
        }
        else {
            holder.imageStatusStart.alpha = 0f
            holder.tvStatus.text = treino.progresso
            holder.tvStatus.visibility = View.VISIBLE
        }
    }

    override fun getItemCount() = lista.size




}