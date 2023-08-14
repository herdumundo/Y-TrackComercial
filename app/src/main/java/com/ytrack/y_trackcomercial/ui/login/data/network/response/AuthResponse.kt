data class UsuarioResponse(
    val Sesion: Boolean ,
    val Token: String,
    val Usuario: List<Usuario>,
    val RutasAccesos: List<RutasAccesos>
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
    val yemsys_role: yemsys_role
)

data class yemsys_role(
    val id: Int,
    val descripcion: String,
    val yemsys_permisos_modulos: List<yemsys_permisos_modulos>
)

data class yemsys_permisos_modulos(
    val id: Int,
    val idModulo: Int,
    val name: String,
    val icono: String,
    val idRolUser: Int,
    val children: List<children>
)

data class children(
    val id: Int,
    val idPermisoModulo: Int,
    val name: String,
    val idFormulario: Int,
    val icono: String,
    val ruta: String
)

data class RutasAccesos(
    val id: Int,
    val idPermisoModulo: Int,
    val name: String,
    val idFormulario: Int,
    val icono: String,
    val ruta: String
)
