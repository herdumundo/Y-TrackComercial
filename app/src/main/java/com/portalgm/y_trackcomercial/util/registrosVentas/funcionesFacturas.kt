package com.portalgm.y_trackcomercial.util.registrosVentas


object funcionesFacturas {

fun determinarNaturalezaReceptor(LicTradNum: String): Int {
    return when {
        LicTradNum.indexOf('-') == 0 -> 2 // NO CONTRIBUYENTE 2
        LicTradNum.indexOf('-') > 0 && LicTradNum.length == 10 && LicTradNum != "44444401-7" -> 1 // JURIDICO 2 Y ES CONTRIBUYENTE 1
        LicTradNum == "44444401-7" -> 1 // ES CONTRIBUYENTE SIN NOMBRE Y PONER JURIDICO 2 DEFAULT.
        LicTradNum.indexOf('-') > 0 && LicTradNum.length < 10 -> 1 // SI ES PERSONA FISICA 1 Y ES CONTRIBUYENTE
        else -> 2
    }
}

    fun determinarTipoContribuyente(LicTradNum: String): Int {
        return when {
            LicTradNum.indexOf('-') == 0 -> 3 // NO CONTRIBUYENTE 2
            LicTradNum.indexOf('-') > 0 && LicTradNum.length == 10 && LicTradNum != "44444401-7" -> 2 // JURIDICO 2 Y ES CONTRIBUYENTE 1
            LicTradNum == "44444401-7" -> 2 // ES CONTRIBUYENTE SIN NOMBRE Y PONER JURIDICO 2 DEFAULT.
            LicTradNum.indexOf('-') > 0 && LicTradNum.length < 10 -> 1 // SI ES PERSONA FISICA 1 Y ES CONTRIBUYENTE
            else -> 2
        }
    }

}