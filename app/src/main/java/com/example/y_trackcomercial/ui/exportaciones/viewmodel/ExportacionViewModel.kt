package com.example.y_trackcomercial.ui.exportaciones.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.y_trackcomercial.model.models.EnviarAuditoriaTrailRequest
import com.example.y_trackcomercial.model.models.EnviarVisitasRequest
import com.example.y_trackcomercial.usecases.exportacionAuditTrail.CountAuditTrailUseCase
import com.example.y_trackcomercial.usecases.exportacionAuditTrail.EnviarAuditTrailPendientesUseCase
import com.example.y_trackcomercial.usecases.exportacionAuditTrail.GetAuditTrailPendienteUseCase
import com.example.y_trackcomercial.usecases.exportacionVisitas.CountCantidadPendientes
import com.example.y_trackcomercial.usecases.exportacionVisitas.EnviarVisitasPendientesUseCase
import com.example.y_trackcomercial.usecases.exportacionVisitas.GetVisitasPendientesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class ExportacionViewModel @Inject constructor(
    private val countCantidadPendientes: CountCantidadPendientes,
    private val countAuditTrailUseCase: CountAuditTrailUseCase,
    private val getVisitasPendientesUseCase: GetVisitasPendientesUseCase,
    private val getAuditTrailPendienteUseCase: GetAuditTrailPendienteUseCase,
    private val enviarVisitasPendientesUseCase: EnviarVisitasPendientesUseCase,
    private val enviarAuditTrailPendientesUseCase: EnviarAuditTrailPendientesUseCase,
) : ViewModel() {

    private val _visitasCount: MutableLiveData<Int> = MutableLiveData()
    val visitasCount: LiveData<Int> = _visitasCount

    private val _auditTrailCount: MutableLiveData<Int> = MutableLiveData()
    val auditTrailCount: LiveData<Int> = _auditTrailCount

    private val _loadingVisitas: MutableLiveData<Boolean> = MutableLiveData()
    val loadingVisitas: LiveData<Boolean> = _loadingVisitas

    private val _loadingAuditTrail: MutableLiveData<Boolean> = MutableLiveData()
    val loadingAuditTrail: LiveData<Boolean> = _loadingAuditTrail


    fun getTablasRegistradas() {
        viewModelScope.launch(Dispatchers.IO) {
            val cantPendientesVisitas = countCantidadPendientes.ContarCantidadPendientes()
            val cantPendientesAuditTrail = countAuditTrailUseCase.CountPendientesExportacion()
            withContext(Dispatchers.Main) {
                _visitasCount.value = cantPendientesVisitas
                _auditTrailCount.value = cantPendientesAuditTrail
            }
        }
    }

    fun enviarPendientes(tipoRegistro: Int) {
        viewModelScope.launch {
            try {
                when (tipoRegistro) {
                    1 -> {
                        if (!_loadingVisitas.value!!) {
                            _loadingVisitas.value = true
                            val visitasPendientes =
                                getVisitasPendientesUseCase.getVisitasPendientes()
                            val enviarVisitasRequest = EnviarVisitasRequest(visitasPendientes)
                            enviarVisitasPendientesUseCase.enviarVisitasPendientes(
                                enviarVisitasRequest
                            )
                            _loadingVisitas.value = false
                        }
                    }

                    2 -> {
                        if (!loadingAuditTrail.value!!) {
                            _loadingAuditTrail.value = true
                            val auditTrailPendientes =
                                getAuditTrailPendienteUseCase.getAuditTrailPendientes()
                            val enviarAuditTrailRequest =
                                EnviarAuditoriaTrailRequest(auditTrailPendientes)
                            enviarAuditTrailPendientesUseCase.enviarAuditTrailPendientes(
                                enviarAuditTrailRequest
                            )
                            _loadingAuditTrail.value = false
                        }
                    }
                }

                getTablasRegistradas()
            } catch (e: Exception) {
                // Manejar la excepción si es necesario
            }
        }
    }

    fun setFalseLoading()
    {
        _loadingAuditTrail.value = false
        _loadingVisitas.value = false
    }
}