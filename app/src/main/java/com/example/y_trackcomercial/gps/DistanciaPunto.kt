package com.example.y_trackcomercial.gps

import android.util.Log

fun calculoMetrosPuntosGps( latitudOrigen :Double,longitudOrigen:Double): Int {
    var metros = 0
    val radioTierra = 6371.0 // en kilómetros

    // UBICACION ACTUAL DEL DISPOSITIVO MOVIL
  /*  val latitudOrigen = -25.327519768011733
    val longitudOrigen = -57.56995713407824
*/
    // UBICACION DEL PUNTO DE VENTA
    val latitudDestino = -25.31708118246417
    val longitudDestino = -57.57165013246648

    val dLat = Math.toRadians(latitudDestino - latitudOrigen)
    val dLng = Math.toRadians(longitudDestino - longitudOrigen)

    val sindLat = Math.sin(dLat / 2)
    val sindLng = Math.sin(dLng / 2)
    val va1 = Math.pow(sindLat, 2.0) + Math.pow(
        sindLng,
        2.0
    ) * Math.cos(Math.toRadians(latitudOrigen)) * Math.cos(Math.toRadians(latitudDestino))
    val va2 = 2 * Math.atan2(Math.sqrt(va1), Math.sqrt(1 - va1))
    val distancia = radioTierra * va2
    val ad = distancia * 1000
    metros = ad.toInt()

    Log.i("Localizacion", "USTED ESTA A $metros METROS DEL PUNTO DE VENTA")

    return metros
}

