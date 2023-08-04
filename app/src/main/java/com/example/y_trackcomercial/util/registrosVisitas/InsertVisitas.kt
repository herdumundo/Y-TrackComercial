package com.example.y_trackcomercial.util.registrosVisitas

import com.example.y_trackcomercial.model.entities.registro_entities.VisitasEntity
import java.time.LocalDateTime

  fun crearVisitaEntity(
    latitudUsuario: Double,
    longitudUsuario: Double,
    porcentajeBateriaVal: Int,
    distanciaMetros: Int,
    estadoVisita: String,
    tipoRegistro: String,
    latitudPV: Double,
    longitudPV: Double,
    idA: Long = 0,
    exportado: Boolean ,
    ocrdName: String,
    tardia: String,
    idTurno: Int,
    tipoCierre: String,
    rol : String,
    idUsuario : Int,
    idOcrd: String,
    idRol: Int,
    pendienteSincro: String,
    secuencia:Int,
    id:Long


): VisitasEntity {
    return VisitasEntity(
        id = id,
        idUsuario = idUsuario,
        idOcrd = idOcrd,
        createdAt = LocalDateTime.now().toString(),
        createdAtLong = System.currentTimeMillis(),
        latitudUsuario = latitudUsuario,
        longitudUsuario = longitudUsuario,
        porcentajeBateria = porcentajeBateriaVal,
        distanciaMetros = distanciaMetros,
        estadoVisita = estadoVisita,
        idRol = idRol,
        idA = idA,
        tipoRegistro = tipoRegistro,
        latitudPV = latitudPV,
        longitudPV = longitudPV,
        ocrdName = ocrdName,
        tarde = tardia,
        idTurno = idTurno,
        tipoCierre = tipoCierre,
        rol = rol,
        exportado = exportado,
        pendienteSincro=pendienteSincro,
        secuencia = secuencia
    )
}