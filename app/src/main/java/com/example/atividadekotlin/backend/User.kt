package com.example.atividadekotlin.backend

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var nome: String,
    var telefone: String ,
    var email: String,
    var senha : String,
    var local : String,
    var createdAt : Date

)