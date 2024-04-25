package com.portalgm.y_trackcomercial.util.impresion

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.portalgm.y_trackcomercial.data.model.models.OinvPosWithDetails
import com.portalgm.y_trackcomercial.util.HoraActualUtils
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

class layoutFactura {
    fun layoutFactura(json:  List<OinvPosWithDetails>, qr: String,cdc:String):String {
        //val gson = Gson()
        //val listType = object : TypeToken<List<OinvPosWithDetails>>(){}.type
       // val oinvList: List<OinvPosWithDetails> = gson.fromJson(json, listType)
        val decimalFormatSymbols = DecimalFormatSymbols(Locale.US)
        decimalFormatSymbols.groupingSeparator = '.' // Punto como separador de miles
        val formatter = DecimalFormat("#,##0", decimalFormatSymbols)
        // Iterar sobre la lista y acceder a los campos oinvPos y details
        var factura="";
        json.forEach { oinvDetails ->
            val details = oinvDetails.details
            val detailsLotes = oinvDetails.detailsLotes // Obtener los detalles de los lotes

            var totalFactura = 0
               factura = "[L]\n" +
                    "[C]<u><font size='small'>VIMAR y CIA S.A</font></u>\n" +
                    "[C]<u><font size='small'>RUC: 80002754-0</font></u>\n" +
                    "[C]<u><font size='small'>Comercio al por mayor de </font></u>\n" +
                    "[C]<u><font size='small'>comestibles, excepto carnes</font></u>\n \n\n" +
                    "[L]<b>Timbrado Nro: ${oinvDetails.oinvPos.timb}</b>   \n" +
                    "[L]<b>Valida desde : ${ HoraActualUtils.convertIsoToDateSimple(oinvDetails.oinvPos.vigenciaTimbrado!!)}</b>   \n" +
                    "[L]<b>Condicion de venta : ${if(oinvDetails.oinvPos.contado=="1"){"CONTADO"} else {"CREDITO"} } </b>   \n" +
                    "[L]<b>Moneda: Guarani</b>   \n" +
                    "[L]<b>IVA INCLUIDO</b>   \n" +
                    "[C]===============================================\n" +
                    "[L]<b>RUC: ${oinvDetails.oinvPos.licTradNum}  </b> \n" +
                    "[L]<b>Cliente: ${oinvDetails.oinvPos.cardName}  </b> \n" +
                    "[L]<b>Nro de factura: ${oinvDetails.oinvPos.numAtCard} </b>   \n" +
                    "[L]<b>Fecha factura:  ${HoraActualUtils.convertirFormatoISO8601AFechaHora(oinvDetails.oinvPos.docDate!!)} </b>   \n" +
                    "[L]\n" +
                    "[C]=========================================\n" +
                    "[L]\n" +

                    "[L]<b>Impu</b>  <b>Cantidad</b>    <b>Precio</b>       [R]<b>Total</b>     \n"+
                    "[L]                  <b>Lote</b>                                           \n"+
                    "[L]------------------------------------------------\n"
            // Añadir cada detalle al texto
            for (detail in details) {
                totalFactura +=  detail.quantity.toInt() * detail.priceAfterVat.toInt();
                val totalPrice = detail.quantity .toInt()* detail.priceAfterVat.toInt()
                val formattedTotalPrice = formatter.format(totalPrice)
                val formattedprice = formatter.format(detail.priceAfterVat.toInt())
                val espCab1 ="         " //
                val espCab2 ="       " //
                val esCab3  ="      "

                val esp1 = "         " //
                val esp2 = "        " //
                val es3 = "       "

                val espaciosCab = when (detail.quantity.length) {
                    1 -> espCab1
                    2 -> espCab2
                    else -> esCab3
                }

                factura +="[L]<b>${detail.itemCode}-${detail.itemName}</b>        \n" +
                        "[L]<b>${detail.taxCode}</b>$espaciosCab<b>${detail.quantity}</b>        <b>${formattedprice}</b>  [R]<b>$formattedTotalPrice</b>\n"

                // Iterar sobre los detalles de los lotes para este item
                val lotesParaItem = detailsLotes.filter { it.itemCode == detail.itemCode }
                for (lote in lotesParaItem) {
                    val espacios = when (lote.quantity.length) {
                        1 -> esp1
                        2 -> esp2
                        else -> es3
                    }
                    factura += "[L]$espacios${lote.quantity}        ${lote.lote} \n"
                }
                factura +="[C]--------------------------------------------\n"
            }
            val totalInvoice = formatter.format(totalFactura)
            val fivePercentOfTotal = formatter.format(totalFactura * 0.05)   // Calcular el 5%

            factura += "[L]<font size='small'> TOTAL A PAGAR:              [L]<b>Gs. </b>[R]<b>$totalInvoice</b></font>\n" +
                    "[C]-----------------------------------------\n" +
                    "[L]<font size='small'> Total Gravadas 10%:         [L]<b>Gs. </b>[R]<b>0</b></font>\n" +
                    "[L]<font size='small'> Total Gravadas 5%:          [L]<b>Gs. </b>[R]<b>$totalInvoice</b></font>\n" +
                    "[L]<font size='small'> Total Exentas%:             [L]<b>Gs. </b>[R]<b>0</b></font>\n" +
                    "[C]-----------------------------------------\n" +
                    "[L]<font size='small'> Total IVA 10%:              [L]<b>Gs. </b>[R]<b>0</b></font>\n" +
                    "[L]<font size='small'> Total IVA 5%:               [L]<b>Gs. </b>[R]<b>$fivePercentOfTotal</b></font>\n" +
                    "[L]<font size='small'> Total IVA:                  [L]<b>Gs. </b>[R]<b>0</b></font>\n" +
                    "[C]-----------------------------------------\n\n" +
                    "[C]<u><font size='small'>*** Gracias por su compra ***</font></u>\n\n" +
                    "[C]<u><font size='small'>ESTE DOCUMENTO ES UNA REPRESENTACION</font></u>\n" +
                    "[C]<u><font size='small'>GRAFICA DE UN DOCUMENTO ELECTRONICO</font></u>\n\n" +
                    "[C]<u><font size='small'>Informacion de interes del facturador </font></u>\n" +
                    "[C]<u><font size='small'>electronico emisor</font></u>\n\n" +
                    "[C]<u>Si su documento electronico presenta</u>\n" +
                    "[C]<u>algun error, podra solicitar la</u>\n" +
                    "[C]<u>modificacion dentro de las 72 horas</u>\n" +
                    "[C]<u>siguientes de la emision de este comprobante.</u>\n\n" +
                    "[C]-----------------------------------------\n" +
                    "[C]<u>Para actualizar sus datos personales</u>\n" +
                    "[C]<u>escriba a vimar.fe@yemita.com.py</u>\n" +
                    "[C]<u>Consulte la validez de esta factura</u>\n" +
                    "[C]<u>Electronica con el numero de DCD</u>\n" +
                    "[C]<u>impreso abajo en:</u>\n" +
                    "[L]<u>https://ekuatia.set.gov.py/consultas/</u>\n" +
                    "[L]<u>CDC:</u>\n" +
                    "[L]<u>"+cdc+"</u>\n" +
                    "[L]\n" +
                    "[C]<qrcode size='40'>" + qr + "</qrcode>";

        }
        return factura
    }
}