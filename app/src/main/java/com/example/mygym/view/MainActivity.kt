package com.example.mygym.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mygym.R
import com.example.mygym.`class`.Treino
import com.example.mygym.adapter.TreinoAdapter
import com.example.mygym.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val meusTreinos = listOf(
            Treino(1,"Peitoral", "10/10", "Finalizado", R.drawable.ic_peitoral),
            Treino(2,"Costas", "8/12", "Em progresso", R.drawable.ic_costas),
            Treino(3,"Pernas", "0/10", "Aguardando inicio", R.drawable.ic_pernas),
            Treino(4, "Braço", "0/10", "Aguardando inicio", R.drawable.ic_braco),
        )
        binding.rvTreinos.layoutManager = LinearLayoutManager(this)
        val adapter = TreinoAdapter(meusTreinos) { treinoClicado ->
            val intent = Intent(this, FichaTreinoActivity::class.java)
            startActivity(intent)
        }
        binding.rvTreinos.adapter = adapter
    }
}