package com.portalgm.y_trackcomercial.util

import com.google.gson.Gson
import com.portalgm.y_trackcomercial.data.model.entities.OCRDEntity
import okio.ByteString

class calculos {


    fun sonDatosIguales(datosNuevos: Any, datosAntiguos: Any): Boolean {
        val gson = Gson()
        val jsonAntiguos = gson.toJson(datosAntiguos).replace("\\s".toRegex(), "")
        val jsonNuevos = gson.toJson(datosNuevos).replace("\\s".toRegex(), "")

        val longitudAntiguos = jsonAntiguos.length
        val longitudNuevos = jsonNuevos.length


        if (jsonNuevos == jsonAntiguos) {
            println("SON DATOS IGUALES")
        } else {
            println("NO SON DATOS IGUALES")
        }

        return jsonNuevos == jsonAntiguos
    }

    fun calculateSizeInKB(jsonString: ByteString): Double {
        // Convierte la cadena JSON a bytes usando UTF-8
        val bytes = jsonString.toByteArray()

        // Calcula el tamaño en KB dividiendo la longitud en bytes por 1024
        val sizeInKB = bytes.size.toDouble() / 1024.0

        return sizeInKB
    }

    fun convertirOcrdRoomAJSON(ocrdRoom: List<OCRDEntity>): String {
        // Usa Gson para convertir la lista de objetos en una cadena JSON
        val gson = Gson()
        return gson.toJson(ocrdRoom)
    }
}