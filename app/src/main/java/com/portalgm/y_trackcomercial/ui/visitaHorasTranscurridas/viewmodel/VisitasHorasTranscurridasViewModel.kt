package com.portalgm.y_trackcomercial.ui.visitaHorasTranscurridas.viewmodel


 import androidx.lifecycle.LiveData
 import androidx.lifecycle.MutableLiveData
 import androidx.lifecycle.ViewModel
 import androidx.lifecycle.viewModelScope
 import com.portalgm.y_trackcomercial.data.model.models.HorasTranscurridasPv
import com.portalgm.y_trackcomercial.usecases.visitas.GetHorasTranscurridasVisitasUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
 import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class VisitasHorasTranscurridasViewModel  @Inject constructor(
    private val getHorasTranscurridasVisitasUseCase:GetHorasTranscurridasVisitasUseCase

) : ViewModel(){
    private val _listaHorasTranscurridas: MutableLiveData<List<HorasTranscurridasPv>> = MutableLiveData()
    val listaHorasTranscurridas: LiveData<List<HorasTranscurridasPv>> = _listaHorasTranscurridas

    fun obtenerLista(){

          viewModelScope.launch  {
              _listaHorasTranscurridas.value =getHorasTranscurridasVisitasUseCase.getHorasTranscurridasVisitas()
        }
    }
}