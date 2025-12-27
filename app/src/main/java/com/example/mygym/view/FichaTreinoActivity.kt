package com.example.mygym.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mygym.R
import com.example.mygym.adapter.FichaTreinoAdapter
import com.example.mygym.adapter.TreinoAdapter
import com.example.mygym.`class`.FichaTreino
import com.example.mygym.`class`.Treino
import com.example.mygym.databinding.ActivityFichaTreinoBinding

class FichaTreinoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFichaTreinoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFichaTreinoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val minhaFicha = listOf(
            FichaTreino(1, "Supino maquina", 4, 1, "10/12 rep"),
            FichaTreino(2, "Crucifixo maquina", 3, 0, "12/15 rep" ),
            FichaTreino(3, "Supino inclinado 45º", 3, 0, "12/15 rep")
        )
        binding.rvFicha.layoutManager = LinearLayoutManager(this)
        val adapter = FichaTreinoAdapter(minhaFicha) { treinoClicado ->
            Toast.makeText(this, "Teste", Toast.LENGTH_SHORT).show()
        }
        binding.rvFicha.adapter = adapter
    }
}