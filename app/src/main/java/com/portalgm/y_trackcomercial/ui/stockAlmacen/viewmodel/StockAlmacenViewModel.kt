package com.portalgm.y_trackcomercial.ui.stockAlmacen.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.portalgm.y_trackcomercial.data.model.models.ventas.DatosDetalleLotes
import com.portalgm.y_trackcomercial.usecases.ventas.stockAlmacen.GetStockLotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StockAlmacenViewModel @Inject constructor(
    private val getStockLotesUseCase: GetStockLotesUseCase,
) : ViewModel() {
    private val _stockLotes = MutableLiveData<List<DatosDetalleLotes>>()
    val stockLotes: LiveData<List<DatosDetalleLotes>> = _stockLotes


      fun obtenerStockLotes() {
        viewModelScope.launch {
            val listaLotesPorProductos = getStockLotesUseCase.Obtener()
            _stockLotes.value = listaLotesPorProductos
        }
    }
}
