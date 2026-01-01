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
            intent.putExtra("categoria_id",treinoClicado.id )
            startActivity(intent)
        }
        binding.rvTreinos.adapter = adapter
        configurarBottomNavigation()
        binding.bottomNavigation.selectedItemId = R.id.nav_treino
    }
    private fun configurarBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_treino -> {
                    // Só abre se já não estiver nela
                    if (this !is MainActivity) {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish() // Fecha a atual para dar o efeito de troca
                    }
                    true
                }
                R.id.nav_perfil -> {
                    if (this !is PerfilActivity) {
                        val intent = Intent(this, PerfilActivity::class.java)
                        startActivity(intent)
                        finish() // O finish() faz a tela "sumir" enquanto a outra abre
                    }
                    true
                }
                else -> false
            }
        }
    }
}