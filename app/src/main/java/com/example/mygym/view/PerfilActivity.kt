package com.example.mygym.view

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mygym.R
import com.example.mygym.databinding.ActivityPerfilBinding
import com.example.mygym.databinding.DialogSaveBinding

class PerfilActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPerfilBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

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