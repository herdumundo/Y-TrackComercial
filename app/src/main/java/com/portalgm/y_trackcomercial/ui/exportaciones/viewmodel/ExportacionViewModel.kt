package com.portalgm.y_trackcomercial.ui.exportaciones.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.portalgm.y_trackcomercial.data.api.request.EnviarAuditoriaTrailRequest
import com.portalgm.y_trackcomercial.data.api.request.EnviarLotesDeActividadesRequest
import com.portalgm.y_trackcomercial.data.api.request.EnviarLotesDeMovimientosRequest
import com.portalgm.y_trackcomercial.data.api.request.EnviarLotesDeUbicacionesNuevasRequest
import com.portalgm.y_trackcomercial.data.api.request.EnviarVisitasRequest
import com.portalgm.y_trackcomercial.usecases.auditLog.CountLogPendientesUseCase
import com.portalgm.y_trackcomercial.usecases.auditLog.EnviarLogPendientesUseCase
import com.portalgm.y_trackcomercial.usecases.auditLog.GetLogPendienteUseCase
import com.portalgm.y_trackcomercial.usecases.exportacionAuditTrail.CountAuditTrailUseCase
import com.portalgm.y_trackcomercial.usecases.exportacionAuditTrail.EnviarAuditTrailPendientesUseCase
import com.portalgm.y_trackcomercial.usecases.exportacionAuditTrail.GetAuditTrailPendienteUseCase
import com.portalgm.y_trackcomercial.usecases.exportacionVisitas.CountCantidadPendientes
import com.portalgm.y_trackcomercial.usecases.exportacionVisitas.EnviarVisitasPendientesUseCase
import com.portalgm.y_trackcomercial.usecases.exportacionVisitas.GetVisitasPendientesUseCase
import com.portalgm.y_trackcomercial.usecases.inventario.CountMovimientoUseCase
import com.portalgm.y_trackcomercial.usecases.inventario.EnviarMovimientoPendientesUseCase
import com.portalgm.y_trackcomercial.usecases.inventario.GetMovimientoPendientesUseCase
import com.portalgm.y_trackcomercial.usecases.newPass.CountNewPassPendienteUseCase
import com.portalgm.y_trackcomercial.usecases.newPass.ExportarNewPassPendientesUseCase
import com.portalgm.y_trackcomercial.usecases.newPass.GetNewPassPendientesUseCase
import com.portalgm.y_trackcomercial.usecases.nuevaUbicacion.CountUbicacionesNuevasPendientesUseCase
import com.portalgm.y_trackcomercial.usecases.nuevaUbicacion.ExportarNuevasUbicacionesPendientesUseCase
import com.portalgm.y_trackcomercial.usecases.nuevaUbicacion.GetNuevasUbicacionesPendientesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ExportacionViewModel @Inject constructor(
    private val countCantidadPendientes: CountCantidadPendientes,
    private val countAuditTrailUseCase: CountAuditTrailUseCase,
    private val countLogPendientesUseCase: CountLogPendientesUseCase,
    private val countMovimientoUseCase: CountMovimientoUseCase,
    private val countUbicacionesNuevasPendientesUseCase: CountUbicacionesNuevasPendientesUseCase,
    private val countNewPassPendienteUseCase: CountNewPassPendienteUseCase,

    private val getVisitasPendientesUseCase: GetVisitasPendientesUseCase,
    private val getAuditTrailPendienteUseCase: GetAuditTrailPendienteUseCase,
    private val getLogPendienteUseCase: GetLogPendienteUseCase,
    private val getMovimientoPendientesUseCase: GetMovimientoPendientesUseCase,
    private val getNuevasUbicacionesPendientesUseCase: GetNuevasUbicacionesPendientesUseCase,
    private val getNewPassPendientesUseCase: GetNewPassPendientesUseCase,


    private val enviarVisitasPendientesUseCase: EnviarVisitasPendientesUseCase,
    private val enviarAuditTrailPendientesUseCase: EnviarAuditTrailPendientesUseCase,
    private val enviarLogPendientesUseCase: EnviarLogPendientesUseCase,
    private val enviarMovimientoPendientesUseCase: EnviarMovimientoPendientesUseCase,
    private val enviarNuevasUbicacionesPendientesUseCase: ExportarNuevasUbicacionesPendientesUseCase,
    private val enviarNewPassPendientesUseCase: ExportarNewPassPendientesUseCase,


    ) : ViewModel() {

    private val _visitasCount: MutableLiveData<Int> = MutableLiveData()
    val visitasCount: LiveData<Int> = _visitasCount

    private val _auditTrailCount: MutableLiveData<Int> = MutableLiveData()
    val auditTrailCount: LiveData<Int> = _auditTrailCount

    private val _logCount: MutableLiveData<Int> = MutableLiveData()
    val logCount: LiveData<Int> = _logCount

    private val _movimientosCount: MutableLiveData<Int> = MutableLiveData()
    val movimientosCount: LiveData<Int> = _movimientosCount

    private val _ubicacionesNuevasCount: MutableLiveData<Int> = MutableLiveData()
    val ubicacionesNuevasCount: LiveData<Int> = _ubicacionesNuevasCount

    private val _newPassCount: MutableLiveData<Int> = MutableLiveData()
    val newPassCount: LiveData<Int> = _newPassCount


    private val _loadingVisitas: MutableLiveData<Boolean> = MutableLiveData()
    val loadingVisitas: LiveData<Boolean> = _loadingVisitas

    private val _loadingNuevasUbicaciones: MutableLiveData<Boolean> = MutableLiveData()
    val loadingNuevasUbicaciones: LiveData<Boolean> = _loadingNuevasUbicaciones

    private val _loadingAuditTrail: MutableLiveData<Boolean> = MutableLiveData()
    val loadingAuditTrail: LiveData<Boolean> = _loadingAuditTrail

    private val _loadingLog: MutableLiveData<Boolean> = MutableLiveData()
    val loadingLog: LiveData<Boolean> = _loadingLog

    private val _loadingMovimientos: MutableLiveData<Boolean> = MutableLiveData()
    val loadingMovimientos: LiveData<Boolean> = _loadingMovimientos

    private val _loadingNewPass: MutableLiveData<Boolean> = MutableLiveData()
    val loadingNewPass: LiveData<Boolean> = _loadingNewPass
    val batchSize = 5


    fun getTablasRegistradas(tipoRegistro: Int) {

        viewModelScope.launch(Dispatchers.IO) {
            val cantPendientes = when (tipoRegistro) {
                1 -> countCantidadPendientes.ContarCantidadPendientes()
                2 -> countAuditTrailUseCase.CountPendientesExportacion()
                3 -> countLogPendientesUseCase.CountPendientes()
                4 -> countMovimientoUseCase.CountPendientes()
                5 -> countUbicacionesNuevasPendientesUseCase.CountPendientes()
                6 -> countNewPassPendienteUseCase.CountPendientes()

                else -> 0 // O cualquier valor predeterminado si tipoRegistro no es 1 ni 2
            }
            withContext(Dispatchers.Main) {
                when (tipoRegistro) {
                    1 -> _visitasCount.value            = cantPendientes
                    2 -> _auditTrailCount.value         = cantPendientes
                    3 -> _logCount.value                = cantPendientes
                    4 -> _movimientosCount.value        = cantPendientes
                    5 -> _ubicacionesNuevasCount.value  = cantPendientes
                    6 -> _newPassCount.value            =cantPendientes

                }
            }
        }
    }

    fun getTablasRegistradasTotal() {
        viewModelScope.launch(Dispatchers.IO) {
            val cantPendientesVisitas = countCantidadPendientes.ContarCantidadPendientes()
            val cantPendientesAuditTrail = countAuditTrailUseCase.CountPendientesExportacion()
            val cantPendientesLog = countLogPendientesUseCase.CountPendientes()
            val cantPendientesMovimientos = countMovimientoUseCase.CountPendientes()
            val cantUbicacionesNuevasPendientesUseCase = countUbicacionesNuevasPendientesUseCase.CountPendientes()
            val cantNuevoPassPendientesUseCase = countNewPassPendienteUseCase.CountPendientes()

            withContext(Dispatchers.Main) {
                _visitasCount.value = cantPendientesVisitas
                _auditTrailCount.value = cantPendientesAuditTrail
                _logCount.value = cantPendientesLog
                _movimientosCount.value = cantPendientesMovimientos
                _ubicacionesNuevasCount.value=cantUbicacionesNuevasPendientesUseCase
                _newPassCount.value=cantNuevoPassPendientesUseCase

            }
        }
    }

    fun enviarPendientes(tipoRegistro: Int) {
        viewModelScope.launch {
            try {
                when (tipoRegistro) {
                    1 -> {
                        if (!_loadingVisitas.value!!) {
                            if (countCantidadPendientes.ContarCantidadPendientes() > 0) {
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
                    }

                    2 -> {
                        if (!loadingAuditTrail.value!!) {
                            if (countAuditTrailUseCase.CountPendientesExportacion() > 0) {
                                _loadingAuditTrail.value = true
                                val auditTrailPendientes =  getAuditTrailPendienteUseCase.getAuditTrailPendientes()
                                val registrosPorBatches = auditTrailPendientes.chunked(batchSize)
                                registrosPorBatches.forEach { batch ->
                                    // Lógica para enviar el batch al servidor
                                    val enviarAuditTrailRequest = EnviarAuditoriaTrailRequest(batch)
                                    enviarAuditTrailPendientesUseCase.enviarAuditTrailPendientes(enviarAuditTrailRequest)
                                }

                                _loadingAuditTrail.value = false
                            }
                        }
                    }
                    3 -> {
                        if (!loadingLog.value!!) {
                            if (countLogPendientesUseCase.CountPendientes() > 0) {
                                _loadingLog.value = true
                                val auditLogPendientes =getLogPendienteUseCase.getAuditLogPendientes()
                                val registrosPorBatches = auditLogPendientes.chunked(batchSize)
                                registrosPorBatches.forEach { batch ->
                                    // Lógica para enviar el batch al servidor
                                    val enviarAuditLogRequest =EnviarLotesDeActividadesRequest(batch)
                                    enviarLogPendientesUseCase.enviarLogPendientes(enviarAuditLogRequest)
                                }

                                _loadingLog.value = false
                            }
                        }
                    }

                    4 -> {
                        if (!loadingMovimientos.value!!) {
                            if (countMovimientoUseCase.CountPendientes() > 0) {
                                _loadingMovimientos.value = true
                                val movimientosPendientes =  getMovimientoPendientesUseCase.GetPendientes()
                                val enviarmovimientosRequest = EnviarLotesDeMovimientosRequest(movimientosPendientes)
                                enviarMovimientoPendientesUseCase.enviarPendientes(enviarmovimientosRequest)
                                _loadingMovimientos.value = false
                            }
                        }
                    }
                    5 -> {
                        if (!loadingNuevasUbicaciones.value!!) {
                            if (countUbicacionesNuevasPendientesUseCase.CountPendientes() > 0) {
                                _loadingNuevasUbicaciones.value = true
                                val lotesPendientes =
                                    getNuevasUbicacionesPendientesUseCase.GetPendientes()
                                val enviarmovimientosRequest =
                                    EnviarLotesDeUbicacionesNuevasRequest(lotesPendientes)
                                enviarNuevasUbicacionesPendientesUseCase.enviarPendientes(
                                    enviarmovimientosRequest
                                )
                                _loadingNuevasUbicaciones.value = false
                            }
                        }
                    }
                    6 -> {
                        if (!loadingNewPass.value!!) {
                            if (countNewPassPendienteUseCase.CountPendientes() > 0) {
                                _loadingNewPass.value = true
                                val lotesPendientes =
                                    getNewPassPendientesUseCase.getPendientes()

                                enviarNewPassPendientesUseCase.enviarPendientes(
                                    lotesPendientes
                                )
                                _loadingNewPass.value = false
                            }
                        }
                    }


                }
                getTablasRegistradas(tipoRegistro)
            } catch (e: Exception) {
              Log.i("Mensaje",e.toString())
            }
        }
    }

    fun setFalseLoading() {
        _loadingAuditTrail.value        = false
        _loadingVisitas.value           = false
        _loadingLog.value               = false
        _loadingMovimientos.value       = false
        _loadingNuevasUbicaciones.value = false
        _loadingNewPass.value           = false
    }
}