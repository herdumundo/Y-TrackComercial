package com.portalgm.y_trackcomercial.ui.facturacion.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.portalgm.y_trackcomercial.data.model.models.OinvPosWithDetails
import com.portalgm.y_trackcomercial.services.bluetooth.servicioBlutu
import com.portalgm.y_trackcomercial.usecases.oinv.GetOinvUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class OinvViewModel  @Inject constructor(
    private val getOinvUseCase: GetOinvUseCase,
    private val context: Context,

    ) : ViewModel( ) {

    fun getLista() {
        viewModelScope.launch {
            try {
                val lista = getOinvUseCase.execute()
                val gson = Gson()
                val json = gson.toJson(lista)
                extractDetailsFromJson(json)
             } catch (e: Exception) {
                Log.e("OinvViewModel", "Error al obtener la lista", e)
            }
        }
    }

    fun extractDetailsFromJson(json: String) {
        val gson = Gson()
        val listType = object : TypeToken<List<OinvPosWithDetails>>() {}.type
        val oinvList: List<OinvPosWithDetails> = gson.fromJson(json, listType)
        val formatter = NumberFormat.getNumberInstance(Locale.US)

        // Iterar sobre la lista y acceder a los campos oinvPos y details
        oinvList.forEach { oinvDetails ->
            val oinvPos = oinvDetails.oinvPos
            val details = oinvDetails.details
            var totalFactura=0
            var texto="[L]\n" +
                    "[C]<u><font size='small'>VIMAR y CIA S.A</font></u>\n" +
                    "[C]<u><font size='small'>RUC: 80002754-0</font></u>\n" +
                    "[C]<u><font size='small'>Comercio al por mayor de </font></u>\n" +
                    "[C]<u><font size='small'>comestibles, excepto carnes</font></u>\n \n\n" +
                    "[L]<b>Timbrado Nro: 16056231</b>   \n" +
                    "[L]<b>Valida desde : 01/12/2022</b>   \n" +
                    "[L]<b>Condicion de venta : CONTADO</b>   \n" +
                    "[L]<b>Moneda: Guarani</b>   \n" +
                    "[L]<b>IVA INCLUIDO</b>   \n" +
                    "[C]===============================================\n" +
                    "[L]<b>Cliente: ${oinvDetails.oinvPos.cardName}  </b> \n" +
                    "[L]<b>Nro de factura: ${oinvDetails.oinvPos.numAtCard} </b>   \n" +
                    "[L]<b>Fecha factura:  ${oinvDetails.oinvPos.docDate} </b>   \n" +
                    "[L]\n" +
                    "[C]================================\n" +
                    "[L]\n" +

                    "[L]<b>Impu</b>  <b>Cantidad</b>    <b>Precio</b>   [R]<b>Total</b>     \n\n"

                    // Añadir cada detalle al texto
                    for (detail in details) {
                        totalFactura += detail.quantity * detail.priceAfterVat;
                        val totalPrice = detail.quantity * detail.priceAfterVat
                        val formattedTotalPrice = formatter.format(totalPrice)

                        texto += "[L]<b>${detail.itemCode}-${detail.itemName}</b>        \n" +
                                "[L]<b>${detail.taxCode}</b>    <b>${detail.quantity}</b>          <b>${detail.priceAfterVat}</b>  [R]<b>$formattedTotalPrice</b>\n"+
                                "[C]-----------------------------------------\n"

                    }
            val totalInvoice = formatter.format(totalFactura)
            val fivePercentOfTotal = formatter.format(totalFactura * 0.05)   // Calcular el 5%

            /*"[L]<b>7840061000150-HUEVOS YEMITA PAQ 180 A D</b>        " +
            "[L]<b>5%</b>    <b>25</b>          <b>174.000</b>  [R]<b>4.350.000</b>\n" +*/

                texto +=  "[L]<font size='small'> TOTAL A PAGAR:              [L]<b>Gs. </b>[R]<b>$totalInvoice</b></font>\n" +
                    "[C]-----------------------------------------\n" +
                    "[L]<font size='small'> Total Gravadas 10%:         [L]<b>Gs. </b>[R]<b>0</b></font>\n" +
                    "[L]<font size='small'> Total Gravadas 5%:          [L]<b>Gs. </b>[R]<b>$totalInvoice</b></font>\n" +
                    "[L]<font size='small'> Total Exentas%:             [L]<b>Gs. </b>[R]<b>0</b></font>\n" +
                    "[C]-----------------------------------------\n" +
                    "[L]<font size='small'> Total IVA 10%:              [L]<b>Gs. </b>[R]<b>0</b></font>\n" +
                    "[L]<font size='small'> Total IVA 5%:               [L]<b>Gs. </b>[R]<b>$fivePercentOfTotal</b></font>\n" +
                    "[L]<font size='small'> Total IVA:                  [L]<b>Gs. </b>[R]<b>0</b></font>\n" +
                    "[C]-----------------------------------------\n\n\n" +
                    "[C]<u><font size='small'>*** Gracias por su compra ***</font></u>\n"+
                    "[L]\n"+
                    "[C]<qrcode size='50'>https://ekuatia.set.gov.py/consultas/qr?nVersion=150&Id=01800027540002001008270022024031416229278373&dFeEmiDE=323032342d30332d31345430343a32363a3032&dRucRec=3602936&dTotGralOpe=360000&dTotIVA=17142.85714286&cItems=1&DigestValue=75465445694c47414f5362616874574772503245656f6e7368673777516c31612f664a4f336435665a77633d&IdCSC=1&cHashQR=de9aed1b11e58c3920ef1c243437d0ecdcffb272324ab2d913f3b0db6b269d6d</qrcode>";

            // Puedes hacer algo con oinvPos y details aquí
            // Por ejemplo, imprimirlos en el log
            Log.i("oinvPos", texto)
            val bluetoothPrinter = servicioBlutu()
            bluetoothPrinter.printBluetooth(context,texto)
        }
    }


}

/*
*

* */