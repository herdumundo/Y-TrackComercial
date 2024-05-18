package com.portalgm.y_trackcomercial.util

import java.math.BigDecimal
import java.math.RoundingMode

object calculosIva {
    fun calcularPrecioSinIva(priceWithVat: Double): Double {
        return (priceWithVat / 1.05)
    }

    fun calcularTotalSinIva(quantity: Double, priceWithVat: Double): Double {
        // Primero calculamos el precio sin IVA por unidad y luego multiplicamos por la cantidad
        val totalSinIva = calcularPrecioSinIva(priceWithVat* quantity)
        // Convertimos a Double para poder aplicar ceil correctamente
        val totalRedondeado = redondeoPersonalizado(totalSinIva)
        // Convertimos de nuevo a Int para retornar un entero
        return totalRedondeado
    }

    fun calcularIva(quantity: Double, priceWithVat: Double): Double {
        // Calcula el 5% de ese total
        var totalIvaInluido=quantity*priceWithVat
        var totalIva5=totalIvaInluido/21.toFloat()
        return  redondeoPersonalizado(totalIva5.toDouble()).toDouble()
    }
    fun calcularIva5(total:Int): Double {
        // Calcula el 5% de ese total
         var totalIva5=total/21.toFloat()
        return  redondeoPersonalizado(totalIva5.toDouble()).toDouble()
    }
    fun redondeoPersonalizado(valor: Double): Double {
        val valorBD = BigDecimal(valor.toString())
        // Redondear hacia arriba solo si el decimal es .5 o más
        val valorRedondeado = valorBD.setScale(0, RoundingMode.HALF_UP)
        return valorRedondeado.toDouble()
    }
    fun formatearCantidad(cantidad: Double): String {
        return if (cantidad % 1 == 0.0) {
            cantidad.toInt().toString()
        } else {
            cantidad.toString()
        }
    }
}