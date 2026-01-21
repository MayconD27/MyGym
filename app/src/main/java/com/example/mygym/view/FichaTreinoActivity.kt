package com.example.mygym.view

import android.app.Dialog
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
import com.example.mygym.`class`.FichaTreino
import com.example.mygym.databinding.ActivityFichaTreinoBinding
import com.example.mygym.databinding.DialogErrorBinding
import com.example.mygym.databinding.DialogTimerBinding
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST


data class FichaTreinoResponse(
    val data: List<FichaTreino>
)
data class FichaTreinoRequest(
    val categoria_id: Int
)
interface  FichaTreinoService{
    @POST("fichaTreino")
    suspend fun getFichaTreino(
        @Body request: FichaTreinoRequest
    ): FichaTreinoResponse

    @POST("atualizarProgresso")
    suspend fun atualizarProgresso(
        @Body request: AtualizarProgressoRequest
    ): retrofit2.Response<Unit>
}
data class AtualizarProgressoRequest(
    val id: Int,
    val qnt_feita: Int
)
class FichaTreinoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFichaTreinoBinding
    private var startTrain = false
    private var categoria_id = 0
    private lateinit var adapter: FichaTreinoAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        categoria_id = intent.getIntExtra("categoria_id", 0)

        binding = ActivityFichaTreinoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(categoria_id <= 0){
            val message = "Categoria não encontrada"
            popUpError(message)
        }
        var minhaFicha = listOf<FichaTreino>()
        adapter = FichaTreinoAdapter(minhaFicha) { treinoClicado ->
            if(startTrain && (treinoClicado.qnt_rep > treinoClicado.qnt_feita)){
                exibirPopUp(treinoClicado.id, treinoClicado.time){
                    treinoClicado.qnt_feita ++
                    adapter.notifyDataSetChanged()
                    atualizarProgressoNoServidor(treinoClicado.id, treinoClicado.qnt_feita)
                }
            }else if(startTrain && (treinoClicado.qnt_rep == treinoClicado.qnt_feita)){
                val message = "Você já completou este exercicio"
                popUpAlert("Atenção", message)
            }
            else{
                val message = "O treino ainda não foi iniciado, para inciar clique em inicar treino"
                popUpAlert("Erro",message)
            }

        }
        when(categoria_id){
            1 -> {
                binding.imageHeader.setImageResource(R.drawable.ic_peitoral)
                binding.tvHeaderTitle.text = "Peitoral"
            }
            2 -> {
                binding.imageHeader.setImageResource(R.drawable.ic_costas)
                binding.tvHeaderTitle.text = "Costas"
            }
            3 ->{
                binding.imageHeader.setImageResource(R.drawable.ic_pernas)
                binding.tvHeaderTitle.text = "Pernas"
            }
            4 -> {
                binding.imageHeader.setImageResource(R.drawable.ic_braco)
                binding.tvHeaderTitle.text = "Braço"
            }
            else ->{
                binding.imageHeader.alpha = 0f
                binding.tvHeaderTitle.text = "Categoria não encontrada"
            }
        }

        val retrofit = retrofit2.Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/api/")
            .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
            .build()
        val service = retrofit.create(FichaTreinoService::class.java)

        lifecycleScope.launch {
            try {
                val requestBody = FichaTreinoRequest(categoria_id)
                val response = service.getFichaTreino(requestBody)
                val fichaTreino = response.data
                android.util.Log.d("RETROFIT_RES", "Lista recebida: $fichaTreino")
                if(fichaTreino.isNotEmpty()){
                    adapter.atualizarLista(fichaTreino)
                }else{
                    popUpAlert("Atenção","Nenhum treino foi registrado")
                }
            }
            catch (e: Exception){
                android.util.Log.e("RETROFIT_ERRO", "Falha: ${e.message}")
                e.printStackTrace()
            }
        }
        binding.rvFicha.layoutManager = LinearLayoutManager(this)
        binding.btnStartTrain.setOnClickListener {
            binding.btnStartTrain.alpha = 0f
            startTrain = true
        }
        binding.rvFicha.adapter = adapter
    }

    fun exibirPopUp(id:Int , time: Int, onFinish: () -> Unit) {
        val dialog = Dialog(this)
        val bindingpopUp = DialogTimerBinding.inflate(layoutInflater)
        dialog.setContentView(bindingpopUp.root)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        var tempoRestante = time
        val handler = android.os.Handler(android.os.Looper.getMainLooper())

        val runnable = object : Runnable {
            override fun run() {
                if (tempoRestante >= 0) {
                    val textoFormatado = if (tempoRestante < 10) {
                        "0$tempoRestante"
                    } else {
                        tempoRestante.toString()
                    }
                    bindingpopUp.timerCount.text = textoFormatado
                    tempoRestante--
                    handler.postDelayed(this, 1000)
                } else {
                    dialog.dismiss()
                    onFinish()
                }
            }
        }

        handler.post(runnable)
        dialog.setOnDismissListener { handler.removeCallbacks(runnable) }
        dialog.show()
    }
    fun popUpError(message: String){
        val dialog = Dialog(this)
        val bindingPopUp = DialogErrorBinding.inflate(layoutInflater)
        bindingPopUp.msgErro.text = message
        dialog.setContentView(bindingPopUp.root)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        bindingPopUp.btnError.setOnClickListener{
            dialog.dismiss()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        dialog.show()
    }
    fun popUpAlert(title: String, message: String){
        val dialog = Dialog(this)
        val bindingPopUp = DialogErrorBinding.inflate(layoutInflater)
        bindingPopUp.msgErro.text = message
        bindingPopUp.title.text = title
        dialog.setContentView(bindingPopUp.root)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        bindingPopUp.btnError.setOnClickListener{
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun atualizarProgressoNoServidor(id: Int, novaQuantidade: Int) {
        // Reutilizamos o service criado no onCreate
        val retrofit = retrofit2.Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8000/api/")
            .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
            .build()
        val service = retrofit.create(FichaTreinoService::class.java)

        lifecycleScope.launch {
            try {
                val request = AtualizarProgressoRequest(id, novaQuantidade)
                val response = service.atualizarProgresso(request)

                if (response.isSuccessful) {
                    android.util.Log.d("API_UPDATE", "Progresso atualizado com sucesso!")
                }
            } catch (e: Exception) {
                android.util.Log.e("API_UPDATE", "Erro ao sincronizar: ${e.message}")
            }
        }
    }
}