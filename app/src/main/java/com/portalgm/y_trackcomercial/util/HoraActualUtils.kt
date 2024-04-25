package com.portalgm.y_trackcomercial.util

import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object HoraActualUtils {
    fun getCurrentFormattedTime(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        return dateFormat.format(Date())
    }

    fun convertirStringToLocalTime(fecha: String): LocalTime {
        val formatter = DateTimeFormatter.ofPattern("HH:mm:ss", Locale.getDefault())
        return LocalTime.parse(fecha, formatter)
    }
    fun formatIsoDateToReadableDate(isoDate: String): String {
        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        parser.timeZone = TimeZone.getTimeZone("UTC")
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val parsedDate = parser.parse(isoDate)
        return formatter.format(parsedDate)
    }

    fun convertIsoToDateSimple(isoDate: String): String {
        val formatter = DateTimeFormatter.ISO_DATE_TIME
        val dateTime = LocalDateTime.parse(isoDate, formatter)
        return dateTime.toLocalDate().toString()  // Esto devolverá la fecha en formato "2024-04-11"
    }

    fun obtenerFechaHoraActual(): String {
        val fechaHoraActual = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        return fechaHoraActual.format(formatter)
    }

    fun convertirFormatoISO8601AFechaHora(cadena: String): String {
        // Parsear la cadena en formato ISO 8601 a LocalDateTime
        val formatoISO8601 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val fechaHora = LocalDateTime.parse(cadena, formatoISO8601)

        // Formatear la fecha y hora en el nuevo formato
        val nuevoFormato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        return fechaHora.format(nuevoFormato)
    }
}