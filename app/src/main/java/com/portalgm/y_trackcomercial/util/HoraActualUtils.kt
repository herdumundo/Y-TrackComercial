package com.portalgm.y_trackcomercial.util

import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

object HoraActualUtils {
    fun getCurrentFormattedTime(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        return dateFormat.format(Date())
    }

    fun convertirStringToLocalTime(fecha: String): LocalTime {
        val formatter = DateTimeFormatter.ofPattern("HH:mm:ss", Locale.getDefault())
        return LocalTime.parse(fecha, formatter)
    }
}