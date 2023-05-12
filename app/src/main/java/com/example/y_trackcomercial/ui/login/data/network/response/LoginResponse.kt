package com.cursokotlin.jetpackcomposeinstagram.login.data.network.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(@SerializedName("success") val success:Boolean)
/*
data class LoginResponse(
    @SerializedName("usuarios") val usuarios: List<Usuarios>
)*/

data class Usuarios(
    val id: Int,
    val nombre: String,
    val usuario: String,
    val apellido: String,
    val clave: String,
    val area: String,
    val createdAt: String,
    val idRol: Int,
    val yemsys_role: YemsysRole
)

data class YemsysRole(
    val id: Int,
    val descripcion: String,
    val yemsys_permisos_modulos: List<YemsysPermisoModulo>
)

data class YemsysPermisoModulo(
    val id: Int,
    val idModulo: Int,
    val name: String,
    val icono: String,
    val idRolUser: Int,
    val children: List<YemsysPermisoModuloChild>
)

data class YemsysPermisoModuloChild(
    val id: Int,
    val idPermisoModulo: Int,
    val name: String,
    val idFormulario: Int,
    val icono: String,
    val ruta: String
)
