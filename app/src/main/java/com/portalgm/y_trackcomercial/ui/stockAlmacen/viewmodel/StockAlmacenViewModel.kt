package com.portalgm.y_trackcomercial.ui.stockAlmacen.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.portalgm.y_trackcomercial.data.model.models.ventas.DatosDetalleLotes
import com.portalgm.y_trackcomercial.data.model.models.ventas.DatosItemCodesStock
import com.portalgm.y_trackcomercial.usecases.ventas.stockAlmacen.GetStockItemCodeUseCase
import com.portalgm.y_trackcomercial.usecases.ventas.stockAlmacen.GetStockLotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StockAlmacenViewModel @Inject constructor(
    private val getStockLotesUseCase: GetStockLotesUseCase,
    private val getStockItemCodeUseCase: GetStockItemCodeUseCase,
) : ViewModel() {
    private val _stockLotes = MutableLiveData<List<DatosDetalleLotes>>()
    val stockLotes: LiveData<List<DatosDetalleLotes>> = _stockLotes

    private val _stockItemCodes = MutableLiveData<List<DatosItemCodesStock>>()
    val stockItemCodes: LiveData<List<DatosItemCodesStock>> = _stockItemCodes


    fun obtenerStockLotes() {
        viewModelScope.launch {
            val listaLotesPorProductos = getStockLotesUseCase.Obtener()
            _stockLotes.value = listaLotesPorProductos
        }
    }

    fun obtenerStockItemCode() {
        viewModelScope.launch {
            val listaLotesPorProductos = getStockItemCodeUseCase.Obtener()
            _stockItemCodes.value = listaLotesPorProductos
        }
    }
}
