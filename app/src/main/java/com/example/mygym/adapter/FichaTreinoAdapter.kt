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

class FichaTreinoAdapter(private var lista: List<FichaTreino>, private val onItemClick: (FichaTreino) -> Unit): RecyclerView.Adapter<
    FichaTreinoAdapter.FichaTreinoViewHolder>() {

    class FichaTreinoViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val fvtTreino : TextView = view.findViewById(R.id.ftv_workout_title)
        val descFicha : TextView = view.findViewById(R.id.ftv_desc)
        val imageStatus : ImageView = view.findViewById(R.id.img_ft_status_icon_finish)
        val fvtStatus : TextView = view.findViewById(R.id.ftv_text_status)


    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FichaTreinoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card_fich_treino,parent, false)
        return FichaTreinoViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: FichaTreinoViewHolder,
        position: Int
    ) {
        val treino = lista[position]
        holder.fvtTreino.text = treino.nome
        holder.descFicha.text = treino.descricao

        val qnt_rep = treino.qnt_rep
        val qnt_feita = treino.qnt_feita


        if(qnt_rep == qnt_feita){
            holder.imageStatus.alpha = 1f
            holder.fvtStatus.visibility = View.GONE
        }
        else{
            holder.imageStatus.alpha = 0f
            holder.fvtStatus.visibility = View.VISIBLE
            holder.fvtStatus.text = "${qnt_feita}/${qnt_rep}"
        }

        holder.itemView.setOnClickListener {
            onItemClick(treino)
        }
    }

    override fun getItemCount(): Int {
        return lista.size
    }
    // Dentro da classe FichaTreinoAdapter
    fun atualizarLista(novaLista: List<FichaTreino>) {
        this.lista = novaLista // Atualiza a variável 'lista' definida lá no topo
        notifyDataSetChanged()
    }


}