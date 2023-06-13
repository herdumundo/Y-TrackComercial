package com.example.y_trackcomercial.model.entities


import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
/*
@Entity(tableName = "usuario")
data class UsuarioEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val sesion: Boolean,
    val token: String
)*/
@Entity(tableName = "usuario")
data class UsuarioEntity(
    @PrimaryKey val id: Int,
    val nombre: String,
    val usuario: String,
    val apellido: String,
    val clave: String,
    val area: String,
    val createdAt: String,
    val idRol: Int,
    @Embedded val yemsys_role: YemsysRoleEntity
)

data class YemsysRoleEntity(
    @PrimaryKey val id: Int,
    val descripcion: String,
    @Embedded val yemsys_permisos_modulos: List<YemsysPermisosModulosEntity>
)

data class YemsysPermisosModulosEntity(
    @PrimaryKey val id: Int,
    val idModulo: Int,
    val name: String,
    val icono: String,
    val idRolUser: Int,
    val children: List<ChildrenEntity>
)

data class ChildrenEntity(
    @PrimaryKey val id: Int,
    val idPermisoModulo: Int,
    val name: String,
    val idFormulario: Int,
    val icono: String,
    val ruta: String
)
