package com.example.y_trackcomercial.repository


import com.example.y_trackcomercial.data.network.ApiService
 import com.example.y_trackcomercial.model.entities.ChildrenEntity
import com.example.y_trackcomercial.model.entities.UsuarioEntity
import com.example.y_trackcomercial.model.entities.YemsysPermisosModulosEntity
import com.example.y_trackcomercial.model.entities.YemsysRoleEntity
import javax.inject.Inject
class UsuarioRepository @Inject constructor(
    private val apiService: ApiService,
   // private val usuarioDao: UsuarioDao
) {
    /*suspend fun fetchUsuarios() {
        try {
            val usuarioResponse = apiService.getUsuarios()
            val usuarios = usuarioResponse[0].Usuario
            usuarioDao.insertUsuarios(usuarios.map { usuario ->
                UsuarioEntity(
                    id = usuario.id,
                    nombre = usuario.nombre,
                    usuario = usuario.usuario,
                    apellido = usuario.apellido,
                    clave = usuario.clave,
                    area = usuario.area,
                    createdAt = usuario.createdAt,
                    idRol = usuario.idRol,
                    yemsys_role = YemsysRoleEntity(
                        id = usuario.yemsys_role.id,
                        descripcion = usuario.yemsys_role.descripcion,
                        yemsys_permisos_modulos = usuario.yemsys_role.yemsys_permisos_modulos.map { permiso ->
                            YemsysPermisosModulosEntity(
                                id = permiso.id,
                                idModulo = permiso.idModulo,
                                name = permiso.name,
                                icono = permiso.icono,
                                idRolUser = permiso.idRolUser,
                                children = permiso.children.map { child ->
                                    ChildrenEntity(
                                        id = child.id,
                                        idPermisoModulo = child.idPermisoModulo,
                                        name = child.name,
                                        idFormulario = child.idFormulario,
                                        icono = child.icono,
                                        ruta = child.ruta
                                    )
                                }
                            )
                        }
                    )
                )
            })
        } catch (e: Exception) {
            // Manejar la excepción
        }
    }*/
}