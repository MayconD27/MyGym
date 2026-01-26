package com.example.mygym.`class`

import com.google.gson.annotations.SerializedName

data class User(
    val id: Int,
    var nome: String,
    var email: String,
    var endereco: String,
    var altura: String,
    var peso: Float
)