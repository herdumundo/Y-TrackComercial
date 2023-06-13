package com.example.y_trackcomercial.data.network.response

data class UsuarioResponse(
    val Usuario: List<Usuario>
)

data class Usuario(
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
    val yemsys_permisos_modulos: List<YemsysPermisosModulos>
)

data class YemsysPermisosModulos(
    val id: Int,
    val idModulo: Int,
    val name: String,
    val icono: String,
    val idRolUser: Int,
    val children: List<Children>
)

data class Children(
    val id: Int,
    val idPermisoModulo: Int,
    val name: String,
    val idFormulario: Int,
    val icono: String,
    val ruta: String
)
