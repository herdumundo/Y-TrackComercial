package com.example.y_trackcomercial.model

data class UsuariosModel(val id:Int =System.currentTimeMillis().hashCode(), val nombre_usuario:String,val clave:String)