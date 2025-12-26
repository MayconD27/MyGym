package com.example.mygym.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mygym.R
import com.example.mygym.Treino
import com.example.mygym.TreinoAdapter
import com.example.mygym.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val meusTreinos = listOf(
            Treino("Peitoral", "10/10", "Finalizado", R.drawable.ic_peitoral),
            Treino("Costas", "8/12", "Em progresso", R.drawable.ic_costas),
            Treino("Pernas", "0/10", "Aguardando inicio", R.drawable.ic_pernas),
            Treino("Braço", "0/10", "Aguardando inicio", R.drawable.ic_braco),
        )
        binding.rvTreinos.layoutManager = LinearLayoutManager(this)
        binding.rvTreinos.adapter = TreinoAdapter(meusTreinos)
    }
}