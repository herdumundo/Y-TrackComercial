package com.portalgm.y_trackcomercial.ui.anulacionFactura.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.portalgm.y_trackcomercial.data.model.models.OinvPosWithDetails
import com.portalgm.y_trackcomercial.usecases.ventas.oinv.queries.GetOinvByDateUseCase
import com.portalgm.y_trackcomercial.usecases.ventas.oinv.actions.UpdateAnularFacturaOinvUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject


@HiltViewModel
class AnulacionFacturaViewModel @Inject constructor(
    private val getOinvByDateUseCase: GetOinvByDateUseCase,
    private val updateAnularFacturaOinvUseCase: UpdateAnularFacturaOinvUseCase
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

    private val _pantallaConfirmacion = MutableLiveData<Boolean>()
    val pantallaConfirmacion: LiveData<Boolean> = _pantallaConfirmacion

    private val _docEntry = MutableLiveData<Long>()


    private val _dialogPantalla = MutableLiveData<Boolean>()
    val dialogPantalla: LiveData<Boolean> = _dialogPantalla

    private val _mensajePantalla = MutableLiveData<String>()
    val mensajePantalla: LiveData<String> = _mensajePantalla

    private val _cliente = MutableLiveData<String>()
    val  cliente: LiveData<String> = _cliente

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

    fun mensajeConfirmacionCancelacion(docEntry: Long, address: String){
        _pantallaConfirmacion.value=true
        _cliente.value=address
        _docEntry.value=docEntry
    }

    fun cancelar(){
        _pantallaConfirmacion.value=false
    }
    fun anularFactura() {
        _loadingPantalla.value = true
        _pantallaConfirmacion.value = false
        _mensajePantalla.value = "Anulando..."

        viewModelScope.launch {
            updateAnularFacturaOinvUseCase.Update(_docEntry.value!!)
            _loadingPantalla.value = false
            searchFacturasByDate()
        }
    }
}