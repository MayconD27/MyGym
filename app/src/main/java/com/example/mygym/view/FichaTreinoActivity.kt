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
import com.example.mygym.adapter.TreinoAdapter
import com.example.mygym.`class`.FichaTreino
import com.example.mygym.`class`.Treino
import com.example.mygym.databinding.ActivityFichaTreinoBinding
import com.example.mygym.databinding.DialogErrorBinding
import com.example.mygym.databinding.DialogTimerBinding

class FichaTreinoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFichaTreinoBinding
    private var startTrain = false
    private var categoria_id = 0
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
        val minhaFicha = listOf(
            FichaTreino(1, "Supino maquina", 4, 0, "10/12 rep", 60, 1),
            FichaTreino(2, "Crucifixo maquina", 3, 0, "12/15 rep" , 40, 1),
            FichaTreino(3, "Supino inclinado 45º", 3, 0, "12/15 rep", 30, 1)
        )

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


        binding.rvFicha.layoutManager = LinearLayoutManager(this)
        val adapter = FichaTreinoAdapter(minhaFicha) { treinoClicado ->
            if(startTrain && (treinoClicado.qnt_rep > treinoClicado.qnt_feita)){
                exibirPopUp(treinoClicado.id, treinoClicado.time)
            }else{
                val message = "O treino ainda não foi iniciado, para inciar clique em inicar treino"
                popUpAlert(message)
            }

        }
        binding.btnStartTrain.setOnClickListener {
            binding.btnStartTrain.alpha = 0f
            startTrain = true
        }
        binding.rvFicha.adapter = adapter
    }
    fun exibirPopUp(id:Int , time: Int) {
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
    fun popUpAlert(message: String){
        val dialog = Dialog(this)
        val bindingPopUp = DialogErrorBinding.inflate(layoutInflater)
        bindingPopUp.msgErro.text = message
        dialog.setContentView(bindingPopUp.root)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        bindingPopUp.btnError.setOnClickListener{
            dialog.dismiss()
        }
        dialog.show()
    }

}