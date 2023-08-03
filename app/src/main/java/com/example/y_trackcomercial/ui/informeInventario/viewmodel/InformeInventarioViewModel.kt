package com.example.y_trackcomercial.ui.informeInventario.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.y_trackcomercial.usecases.informeInventario.GetInformeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class InformeInventarioViewModel  @Inject constructor(
    private val getInformeUseCase: GetInformeUseCase

) : ViewModel( ) {
    private val _fecha: MutableLiveData<String> = MutableLiveData()
    val fecha: MutableLiveData<String> = _fecha


    private val _grilla: MutableLiveData<String> = MutableLiveData()
    val grilla: MutableLiveData<String> = _grilla

    fun setFecha(date: String){
        _fecha.value=date
    }
    fun getInformeInventario() {

        if(_fecha.value.isNullOrBlank() ){
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
           var informeInventarioList = getInformeUseCase.getInforme(_fecha.value!!)

            withContext(Dispatchers.Main) {
                val tablaHTML = buildString {
                    var   htmlContent=""
                     htmlContent = """
        <html>
        <head>
            <meta name="viewport" content="width=device-width, initial-scale=1.0">

            <style>
             
                th, td {
                    border: 1px solid black;
                    padding: 8px;
                    text-align: center;
                }
            </style>
        </head>
        <body>
            <h2>Listado de registros</h2>""".trimIndent();


                    append("$htmlContent<table>")
                    append("<tr>")
                    append("<th>Nro</th>")
                    append("<th>Punto venta</th>")
                    append("<th>Cantidad</th>")
                    append("<th>Lote</th>")
                    append("<th>Codigo</th>")
                    append("<th>Articulo</th>")
                    append("<th>Codigo Barra</th>")
                    append("<th>Ubicacion</th>")
                    append("<th>Estado</th>")
                    // Agrega aquí las celdas de encabezado para las otras propiedades
                    append("</tr>")

                    for (informe in informeInventarioList) {
                        append("<tr>")
                        append("<td>${informe.id}</td>")
                        append("<td>${informe.ocrdName}</td>")
                        append("<td>${informe.cantidad}</td>")
                        append("<td>${informe.lote}</td>")
                        append("<td>${informe.itemCode}</td>")
                        append("<td>${informe.itemName}</td>")
                        append("<td>${informe.codebar}</td>")
                        append("<td>${informe.ubicacion}</td>")
                        append("<td>${informe.estado}</td>")
                        // Agrega aquí las celdas para las otras propiedades
                        append("</tr>")
                    }

                    append("</table></body>  </html>")
                }
                Log.i("Mensaje", tablaHTML )

                _grilla.value=tablaHTML
            }

        }


 }

    fun limpiarGrilla(){
        _grilla.value=""
    }

}