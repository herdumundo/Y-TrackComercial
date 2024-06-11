package com.portalgm.y_trackcomercial.util.impresion

import android.annotation.SuppressLint
import android.util.DisplayMetrics
import com.dantsu.escposprinter.textparser.PrinterTextParserImg
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.portalgm.y_trackcomercial.R
import com.portalgm.y_trackcomercial.data.model.models.OinvPosWithDetails
import com.portalgm.y_trackcomercial.util.HoraActualUtils
import com.portalgm.y_trackcomercial.util.calculosIva
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

class layoutFactura {
    @SuppressLint("SuspiciousIndentation")
    fun layoutFactura(json:  List<OinvPosWithDetails>, qr: String, cdc:String):String {
        //val gson = Gson()
        //val listType = object : TypeToken<List<OinvPosWithDetails>>(){}.type
       // val oinvList: List<OinvPosWithDetails> = gson.fromJson(json, listType)
        var factura="";
        var condicion=""
        try {


        val decimalFormatSymbols = DecimalFormatSymbols(Locale.US)
        decimalFormatSymbols.groupingSeparator = '.' // Punto como separador de miles
        val formatter = DecimalFormat("#,##0", decimalFormatSymbols)
        // Iterar sobre la lista y acceder a los campos oinvPos y details
        json.forEach { oinvDetails ->
            val details = oinvDetails.details
            val detailsLotes = oinvDetails.detailsLotes // Obtener los detalles de los lotes
            condicion=oinvDetails.oinvPos.contado!!
            var totalFactura = 0.0
               factura = "[L]\n" +
                    "[C]<font size='small'>VIMAR y CIA S.A</font>\n" +
                    "[C]<font size='small'>RUC: 80002754-0</font>\n" +
                    "[C]<font size='small'>Comercio al por mayor de comestibles,</font>\n" +
                    "[C]<font size='small'>excepto carnes.</font>\n" +
                    "[C]<font size='small'>Comercio al por menor de otros</font>\n" +
                    "[C]<font size='small'>productos en comercios</font>\n" +
                    "[C]<font size='small'>no especializados.</font>\n" +
                    "[C]<font size='small'>Asuncion Nro 227 c/ Def. del Chaco</font>\n" +
                    "[C]<font size='small'>Tel:(021)505 725.</font>\n\n" +
                    "[L]<b>Timbrado Nro: ${oinvDetails.oinvPos.timb}</b>   \n" +
                    "[L]<b>Fecha de Inicio de Vigencia: ${ HoraActualUtils.convertIsoToDateSimple(oinvDetails.oinvPos.vigenciaTimbrado!!)}</b>   \n" +
                    "[C]<b>IVA INCLUIDO</b>   \n" +
                    "[C]================================================\n" +
                    "[L]<b>Fact. Electronica Nro: ${oinvDetails.oinvPos.numAtCard} </b>   \n" +
                    "[L]<b>Cond. Vta: ${if(oinvDetails.oinvPos.contado=="1"){"CONTADO"} else {"CREDITO"} } </b>   \n" +
                    "[L]<b>Fecha:  ${HoraActualUtils.convertirFormatoISO8601AFechaHora(oinvDetails.oinvPos.docDate!!)} </b>   \n" +
                    "[L]<b>Cliente: ${oinvDetails.oinvPos.cardName}  </b> \n" +
                    "[L]<b>RUC/CI: ${oinvDetails.oinvPos.licTradNum}  </b> \n" +
                    "[L]<b>Direccion: ${oinvDetails.oinvPos.STREET}  </b> \n" +
                    "[C]================================================\n" +
                    "[L]<font size='small'>Impu. Cantidad    Precio       [R]Total</font>     \n"+
                    "[L]<font size='small'>                  Lote</font>\n"+
                    "[C]================================================\n"
            // Añadir cada detalle al texto
            for (detail in details) {
                totalFactura +=  detail.quantity.toDouble() * detail.priceAfterVat.toDouble();
                val totalPrice = detail.quantity .toDouble()* detail.priceAfterVat.toInt()
                val formattedTotalPrice = formatter.format(totalPrice)
                val formattedprice = formatter.format(detail.priceAfterVat.toInt())
                val cantidadFormateada = calculosIva.formatearCantidad(detail.quantity.toDouble())


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

                factura +="[L]<b>${detail.CodeBars}-${detail.itemCode}-${detail.itemName}</b>        \n" +
                        "[L]<b>${detail.taxCode}</b>$espaciosCab<b>${cantidadFormateada}</b>        <b>${formattedprice}</b>  [R]<b>$formattedTotalPrice</b>\n"

                // Iterar sobre los detalles de los lotes para este item
                val lotesParaItem = detailsLotes.filter { it.itemCode == detail.itemCode }
                for (lote in lotesParaItem) {
                    val espacios = when (lote.quantityCalculado.length) {
                        1 -> esp1
                        2 -> esp2
                        else -> es3
                    }
                    if(lote.itemCode.length>1){
                        factura += "[L]$espacios${lote.quantityCalculado}        ${lote.lote} \n"
                    }
                }
                factura +="[C]================================================\n"
            }//
            val totalInvoice = formatter.format(totalFactura)
            val fivePercentOfTotal = formatter.format(calculosIva.calcularIva5(totalFactura.toInt()) )   // Calcular el 5%

            factura += "[L]<font size='small'> TOTAL A PAGAR:              [L]<b>Gs. </b>[R]<b>$totalInvoice</b></font>\n" +
                    "[C]================================================\n" +
                    "[L]<font size='small'> Total Gravadas 10%:         [L]<b>Gs. </b>[R]<b>0</b></font>\n" +
                    "[L]<font size='small'> Total Gravadas 5%:          [L]<b>Gs. </b>[R]<b>$totalInvoice</b></font>\n" +
                    "[L]<font size='small'> Total Exentas%:             [L]<b>Gs. </b>[R]<b>0</b></font>\n" +
                    "[C]================================================\n" +
                    "[L]<font size='small'> LIQUIDACION IVA</font>\n" +
                    "[L]<font size='small'> Total IVA 10%:              [L]<b>Gs. </b>[R]<b>0</b></font>\n" +
                    "[L]<font size='small'> Total IVA 5%:               [L]<b>Gs. </b>[R]<b>$fivePercentOfTotal</b></font>\n" +
                    "[L]<font size='small'> Total IVA:                  [L]<b>Gs. </b>[R]<b>0</b></font>\n\n\n\n\n";
                    if(!condicion.equals("1")){
            factura +=
                    "[C]------------------------------------------------\n"+
                    "[C]<font size='small'> Firma y Aclaracion</font>\n\n\n"+
                    "[C]------------------------------------------------\n"+
                    "[C]<font size='small'> CI</font>\n"
                    }
            factura +="[C]================================================\n\n" +
                    "[C]<font size='small'>*** Gracias por su compra ***</font>\n\n" +
                    "[C]<font size='small'>ESTE DOCUMENTO ES UNA REPRESENTACION</font>\n" +
                    "[C]<font size='small'>GRAFICA DE UN DOCUMENTO ELECTRONICO</font>\n\n" +
                    "[C]<font size='small'>Informacion de interes del facturador </font>\n" +
                    "[C]<font size='small'>electronico emisor</font>\n\n" +
                    "[C]Si su documento electronico presenta\n" +
                    "[C]algun error, podra solicitar la\n" +
                    "[C]modificacion dentro de las 72 horas\n" +
                    "[C]siguientes de la emision de este comprobante.\n\n" +
                    "[C]================================================\n" +
                    "[C]Para actualizar sus datos personales\n" +
                    "[C]escriba a vimar.fe@yemita.com.py\n" +
                    "[C]Consulte la validez de esta factura\n" +
                    "[C]Electronica con el numero de DCD\n" +
                    "[C]impreso abajo en:\n" +
                    "[L]https://ekuatia.set.gov.py/consultas/\n" +
                    "[L]CDC:\n" +
                    "[L]"+cdc+"\n" +
                    "[L]\n" +
                    "[C]<qrcode size='40'>" + qr + "</qrcode>";

        }
        }catch (e:Exception){
            factura=e.message.toString()
        }
        finally {
            return factura

        }
    }
}