package com.portalgm.y_trackcomercial.ui.reimpresionFactura.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.portalgm.y_trackcomercial.data.model.models.OinvPosWithDetails
import com.portalgm.y_trackcomercial.services.bluetooth.ImpresionResultado
import com.portalgm.y_trackcomercial.services.bluetooth.servicioBluetooth
import com.portalgm.y_trackcomercial.usecases.ventas.oinv.GetOinvByDateUseCase
import com.portalgm.y_trackcomercial.usecases.ventas.oinv.GetOinvUseCase
import com.portalgm.y_trackcomercial.usecases.ventas.oinv.UpdateFirmaOinvUseCase
import com.portalgm.y_trackcomercial.util.firmadorFactura.firmarFactura
import com.portalgm.y_trackcomercial.util.impresion.layoutFactura
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject


@HiltViewModel
class ReimpresionFacturaViewModel @Inject constructor(
    private val getOinvByDateUseCase:GetOinvByDateUseCase,
    private val getOinvUseCase: GetOinvUseCase,
    private val updateFirmaOinvUseCase: UpdateFirmaOinvUseCase, // Inyecta la instancia de la base de datos
): ViewModel() {
    // Fecha seleccionada como MutableStateFlow
    private val _selectedDate = MutableStateFlow(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
    val selectedDate: StateFlow<String> = _selectedDate

    private val _facturas = MutableStateFlow<List<OinvPosWithDetails>>(emptyList())
    val facturas: StateFlow<List<OinvPosWithDetails>> = _facturas

    // Estado adicional para manejar los resultados filtrados, si es necesario
    private val _facturasFiltradas = MutableStateFlow<List<OinvPosWithDetails>?>(null)

    private val _loadingPantalla = MutableLiveData<Boolean>()
    val loadingPantalla: LiveData<Boolean> = _loadingPantalla
    private val _dialogPantalla = MutableLiveData<Boolean>()
    val dialogPantalla: LiveData<Boolean> = _dialogPantalla
    private val _docEntry = MutableLiveData<Long>()

    private val _mensajePantalla = MutableLiveData<String>()
    val mensajePantalla: LiveData<String> = _mensajePantalla

    fun setSelectedDate(date: LocalDate) {
        _selectedDate.value = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    }
    fun searchFacturasByDate() {
        // Ejemplo: Implementa llamada al usecase aquí
        viewModelScope.launch {
            val result = getOinvByDateUseCase.execute(_selectedDate.value)
            _facturas.value = result

            Log.d("Reimpresion",result.toString())
            // Actualiza el estado con las facturas recibidas
        }
    }
    fun imprimir(docEntry: Long) {
        _loadingPantalla.value = true
        _mensajePantalla.value = "Imprimiendo..."
        viewModelScope.launch {
            // Filtrar la lista de facturas para encontrar aquellas con docEntry específico
            val resultado = _facturas.value.filter { factura ->
                factura.oinvPos.docEntry == docEntry
            }
            // Actualizar el estado de facturas filtradas
            _facturasFiltradas.value = resultado

            // Asegúrate de que las operaciones de impresión se realicen en el contexto de I/O
            withContext(Dispatchers.IO) {
                val servicioBluetooth = servicioBluetooth()
                val resultadoImpresion = servicioBluetooth.imprimir(layoutFactura().layoutFactura(_facturasFiltradas.value!!, _facturasFiltradas.value!![0].oinvPos.qr!!, _facturasFiltradas.value!![0].oinvPos.cdc!!))

                // Regresa al hilo principal para actualizar la UI
                withContext(Dispatchers.Main) {
                    when (resultadoImpresion) {
                        is ImpresionResultado.Exito -> {
                            val mensajeExito = resultadoImpresion.mensaje
                            // Manejar el mensaje de éxito, por ejemplo, mostrarlo en un toast o en un log
                            _mensajePantalla.value = mensajeExito
                            _loadingPantalla.value = false
                            _dialogPantalla.value = true
                        }
                        is ImpresionResultado.Error -> {
                            val mensajeError = resultadoImpresion.mensaje
                            // Manejar el mensaje de error, por ejemplo, mostrar un mensaje al usuario indicando el error
                            _mensajePantalla.value = mensajeError
                            _loadingPantalla.value = false
                            _dialogPantalla.value = true
                        }
                    }
                }
            }
        }
    }

    fun prepararFirma(docEntry: Long){
        _loadingPantalla.value = true
        _mensajePantalla.value = "Firmando factura..."
        _docEntry.value=docEntry
        viewModelScope.launch {
             val listaFactura = getOinvUseCase.execute(docEntry)
            firmarFactura.generarStringSiedi(listaFactura,"2")
        }
    }
    fun finalizarFirma(qr: String, xml: String) {
        viewModelScope.launch {
            try {
                val jsonObject = JSONObject(qr)
                updateFirmaOinvUseCase.Update(
                    jsonObject.getString("qr"),
                    xml,
                    jsonObject.getString("cdc"),
                    _docEntry.value!!
                )
                _mensajePantalla.value = "Firmado con exito."
                _loadingPantalla.value = false
                _dialogPantalla.value = true
                searchFacturasByDate()
            } catch (e: Exception) {
                Log.e("OinvViewModel", "Error al obtener la lista", e)
            }
        }
    }
    fun processReceivedData(datosXml: String?, datosQrCdc: String?) {
        if (datosQrCdc.equals("null")) {
            _mensajePantalla.value = "Ha ocurrido en error al firmar \n$datosXml"
            _loadingPantalla.value = false
            _dialogPantalla.value = true
        } else {
            finalizarFirma(datosQrCdc!!, datosXml!!)
        }
    }
}