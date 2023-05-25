package com.example.y_trackcomercial.ui.menuPrincipal

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Login
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Destinos (
    val icono :ImageVector,
    val name: String,
    val ruta: String

        )
{
    object Pantalla1: Destinos(Icons.Filled.Checklist,"Registro Inventario","home")
    object Pantalla2: Destinos(Icons.Filled.Login,"Inicio de visita promotora","home")
    object Pantalla3: Destinos(Icons.Filled.Login,"Inicio de visita supervisora","home")
    object Pantalla4: Destinos(Icons.Filled.Login,"Inicio de visita Coordinadora","registroNuevo")
}
