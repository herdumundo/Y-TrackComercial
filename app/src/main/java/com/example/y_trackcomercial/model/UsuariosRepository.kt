package com.example.y_trackcomercial.model

import com.example.y_trackcomercial.model.dao.UsuariosDao
import com.example.y_trackcomercial.model.entities.UsuariosEntity
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class UsuariosRepository @Inject constructor(private val usuariosDao: UsuariosDao) {

    // val tasks: Flow<List<UsuariosModel>> =usuariosDao.getUsuarios().map {  items-> items.map { UsuariosModel(it.id,it.nombre_usuario,it.clave) }}
    suspend fun add(usuariosModel: UsuariosModel) {

        usuariosDao.insertUsuarios(usuariosModel.toData())
    }

    suspend fun update(usuariosModel: UsuariosModel) {
        usuariosDao.updateUsuarios(usuariosModel.toData())
    }

    suspend fun delete(usuariosModel: UsuariosModel) {
        usuariosDao.deleteUsuarios(usuariosModel.toData())
    }
}

fun UsuariosModel.toData(): UsuariosEntity {
    return UsuariosEntity(this.id, this.nombre_usuario, this.clave)
}