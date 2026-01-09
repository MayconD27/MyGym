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
import retrofit2.http.GET
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

interface TreinoService {
    @GET("treino")
    suspend fun getTreino(): TreinoResponse
}
data class TreinoResponse(
    val data: List<Treino>
)


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var meusTreinos = listOf<Treino>()
        binding.rvTreinos.layoutManager = LinearLayoutManager(this)
        val adapter = TreinoAdapter(meusTreinos) { treinoClicado ->
            val intent = Intent(this, FichaTreinoActivity::class.java)
            intent.putExtra("categoria_id",treinoClicado.id )
            startActivity(intent)
        }
        binding.rvTreinos.adapter = adapter
        configurarBottomNavigation()
        binding.bottomNavigation.selectedItemId = R.id.nav_treino
        val retrofit = retrofit2.Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8000/api/")
            .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
            .build()
        val service = retrofit.create(TreinoService::class.java)

        lifecycleScope.launch {
            val response = service.getTreino()
            val treino = response.data
            android.util.Log.d("RETROFIT_RES", "Lista recebida: $treino")
            if(treino.isNotEmpty()){
                adapter.atualizarLista(treino)
            }else{
                Toast.makeText(this@MainActivity, "Nenhum treino foi registrado", Toast.LENGTH_SHORT).show()
            }
        }
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