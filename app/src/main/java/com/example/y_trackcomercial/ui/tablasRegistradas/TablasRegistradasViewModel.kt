package com.example.y_trackcomercial.ui.tablasRegistradas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.y_trackcomercial.repository.OcrdUbicacionesRepository
import com.example.y_trackcomercial.repository.CustomerRepository
import com.example.y_trackcomercial.repository.HorariosUsuarioRepository
import com.example.y_trackcomercial.repository.LotesListasRepository
import com.example.y_trackcomercial.repository.RutasAccesosRepository
import com.example.y_trackcomercial.repository.registroRepositories.logRepositories.AuditTrailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel

class TablasRegistradasViewModel @Inject constructor(
    private val ocrdRepository: CustomerRepository,
    private val ocrdUbicacionesRepository:  OcrdUbicacionesRepository,
    private val rutasAccesosRepository:  RutasAccesosRepository,
    private val lotesListasRepository:  LotesListasRepository,
    private val horariosUsuarioRepository:  HorariosUsuarioRepository,
    private val auditTrailRepository:  AuditTrailRepository,


) : ViewModel() {

    private val _ocrdCount: MutableLiveData<Int> = MutableLiveData()
    val ocrdCount: LiveData<Int> = _ocrdCount

    private val _ocrdUbicacionesCount: MutableLiveData<Int> = MutableLiveData()
    val ocrdUbicacionesCount: LiveData<Int> = _ocrdUbicacionesCount

    private val _rutasAccesoCount: MutableLiveData<Int> = MutableLiveData()
    val rutasAccesoCount: LiveData<Int> = _rutasAccesoCount

    private val _lotesListasCount: MutableLiveData<Int> = MutableLiveData()
    val lotesListasCount: LiveData<Int> = _lotesListasCount

    private val _horariosUsuarioCount: MutableLiveData<Int> = MutableLiveData()
    val horariosUsuarioCount: LiveData<Int> = _horariosUsuarioCount

    private val _auditTrailCount: MutableLiveData<Int> = MutableLiveData()
    val auditTrailCount: LiveData<Int> = _auditTrailCount

    fun getCustomerCount() {
        viewModelScope.launch(Dispatchers.IO) {
            val cantClientes = ocrdRepository.getOcrdCount()
            val cantUbicaciones = ocrdUbicacionesRepository.getOcrdUbicacionesCount()
            val cantRutasAccesos = rutasAccesosRepository.getRutasAccesosCount()
            val cantListasLotes = lotesListasRepository.getListasLotesCount()
            val cantHorariosUsuario = horariosUsuarioRepository.getTurnosHorariosCountRepository()
            val cantAuditTrail = auditTrailRepository.getAuditTrailCount()

            withContext(Dispatchers.Main) {
                _ocrdCount.value = cantClientes
                _ocrdUbicacionesCount.value = cantUbicaciones
                _rutasAccesoCount.value = cantRutasAccesos
                _lotesListasCount.value=cantListasLotes
                _horariosUsuarioCount.value =cantHorariosUsuario
                _auditTrailCount.value =cantAuditTrail

            }
        }
    }

    init {
        getCustomerCount()
    }

}