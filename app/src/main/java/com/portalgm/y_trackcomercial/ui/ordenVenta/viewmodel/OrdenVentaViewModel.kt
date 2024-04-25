package com.portalgm.y_trackcomercial.ui.ordenVenta.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.portalgm.y_trackcomercial.data.model.models.ventas.OrdenVentaCabItem
import com.portalgm.y_trackcomercial.usecases.ventas.ordenVenta.GetOrdenVentaCabUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdenVentaViewModel @Inject constructor(
    private val getOrdenVentaCabUseCase: GetOrdenVentaCabUseCase
) : ViewModel() {

    private val _listaOrdenVenta = MutableLiveData<List<OrdenVentaCabItem>>()
    val listaOrdenVenta: LiveData<List<OrdenVentaCabItem>> = _listaOrdenVenta

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun obtenerLista() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val resultados = getOrdenVentaCabUseCase.Obtener()
                _listaOrdenVenta.value = resultados
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Considera también limpiar el mensaje de error cuando ya no sea necesario.
    fun limpiarError() {
        _error.value = null
    }
}
