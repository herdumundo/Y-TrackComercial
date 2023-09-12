package com.portalgm.y_trackcomercial.data.model

data class UsuariosModel(val id:Int =System.currentTimeMillis().hashCode(), val nombre_usuario:String,val clave:String)