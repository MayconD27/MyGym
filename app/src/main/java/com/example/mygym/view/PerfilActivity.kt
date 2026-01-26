package com.example.mygym.view

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.mygym.R
import com.example.mygym.`class`.Treino
import com.example.mygym.`class`.User
import com.example.mygym.databinding.ActivityPerfilBinding
import com.example.mygym.databinding.DialogSaveBinding
import kotlinx.coroutines.launch
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT


interface PerfilService {
    @GET("user/1")
    suspend fun getPerfil(): UserResponse

    @PUT("user/1")
    suspend fun updatePerfil(@Body user: User): retrofit2.Response<UserResponse>
}
data class UserResponse(
    val data: List<User>
)

class PerfilActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPerfilBinding
    private lateinit var service: PerfilService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val retrofit = retrofit2.Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8000/api/")
            .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
            .build()
        service = retrofit.create(PerfilService::class.java)
        lifecycleScope.launch {
            try {
                val response = service.getPerfil()
                val userList = response.data
                if (userList.isNotEmpty()) {
                    var usuario = userList[0]
                    binding.nomeUsuario.setText(usuario.nome.toString())
                    binding.emailUsuario.setText(usuario.email.toString())
                    binding.enderecoUsuario.setText(usuario.endereco.toString())
                    binding.alturaUsuario.setText(usuario.altura.toString())
                    binding.pesoUsuario.setText(usuario.peso.toString())
                }
            } catch (e: Exception) {
                android.util.Log.e("RETROFIT_RES", "Falha total: ${e.message}", e)
                Toast.makeText(this@PerfilActivity, "Erro: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }

        configurarBottomNavigation()
        binding.bottomNavigation.selectedItemId = R.id.nav_perfil
        binding.saveBtn.setOnClickListener {
            exibirPopupPersonalizado()
        }
    }
    private fun configurarBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_treino -> {
                    if (this !is MainActivity) {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    true
                }
                R.id.nav_perfil -> {
                    if (this !is PerfilActivity) {
                        val intent = Intent(this, PerfilActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    true
                }
                else -> false
            }
        }
    }
    fun exibirPopupPersonalizado() {
        val dialog = Dialog(this)
        val bindingPop = DialogSaveBinding.inflate(layoutInflater)
        dialog.setContentView(bindingPop.root)

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        bindingPop.btnSalvar.setOnClickListener {
            Toast.makeText(this, "Salvar", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        bindingPop.btnCancel.setOnClickListener {
            Toast.makeText(this, "Cancelar", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        dialog.show()
    }
}