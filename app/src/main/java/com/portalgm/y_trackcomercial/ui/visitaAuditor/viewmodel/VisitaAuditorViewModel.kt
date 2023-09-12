package com.portalgm.y_trackcomercial.ui.visitaAuditor.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.portalgm.y_trackcomercial.data.model.models.OcrdItem
import com.portalgm.y_trackcomercial.repository.CustomerRepository
import com.portalgm.y_trackcomercial.repository.HorariosUsuarioRepository
import com.portalgm.y_trackcomercial.repository.PermisosVisitasRepository
import com.portalgm.y_trackcomercial.repository.registroRepositories.VisitasRepository
import com.portalgm.y_trackcomercial.repository.registroRepositories.logRepositories.LogRepository
import com.portalgm.y_trackcomercial.services.battery.getBatteryPercentage
import com.portalgm.y_trackcomercial.services.developerMode.isDeveloperModeEnabled
import com.portalgm.y_trackcomercial.services.gps.calculoMetrosPuntosGps
import com.portalgm.y_trackcomercial.services.gps.locatioGoogleMaps.LocationService
import com.portalgm.y_trackcomercial.services.time_zone.isAutomaticDateTime
import com.portalgm.y_trackcomercial.services.time_zone.isAutomaticTimeZone
import com.portalgm.y_trackcomercial.util.SharedPreferences
import com.portalgm.y_trackcomercial.util.ValidacionesVisitas
import com.portalgm.y_trackcomercial.util.logUtils.LogUtils
import com.portalgm.y_trackcomercial.util.registrosVisitas.crearVisitaEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import javax.inject.Inject


@HiltViewModel
class VisitaAuditorViewModel @Inject constructor(
    private val customerRepository: CustomerRepository,
    private val visitasRepository: VisitasRepository,
    private val sharedPreferences: SharedPreferences,
    private val permisosVisitasRepository: PermisosVisitasRepository,
    private val horariosUsuarioRepository: HorariosUsuarioRepository,
    private val logRepository: LogRepository,
    private val context: Context

) : ViewModel() {

    private val  locationService:LocationService= LocationService()
    private val _addressesList: MutableList<OcrdItem> = mutableListOf()

    private val _metros: MutableLiveData<Int> = MutableLiveData()
    val metros: MutableLiveData<Int> = _metros

    private val _idOcrd: MutableLiveData<String> = MutableLiveData()
    val idOcrd: MutableLiveData<String> = _idOcrd

    private val _ocrdName: MutableLiveData<String> = MutableLiveData()
    val ocrdName: MutableLiveData<String> = _ocrdName


    private val _showDialog = MutableLiveData<Boolean>()
    val showDialog: LiveData<Boolean> = _showDialog

    private val _mensajeDialog = MutableLiveData<String>()
    val mensajeDialog: LiveData<String> = _mensajeDialog

    private val _showButtonPv = MutableLiveData<Boolean>()
    val showButtonPv: LiveData<Boolean> = _showButtonPv

    private val _buttonPvText = MutableLiveData<String>()
    val buttonPvText: LiveData<String> = _buttonPvText

    private val _showButtonSelectPv = MutableLiveData<Boolean>()
    val showButtonSelectPv: LiveData<Boolean> = _showButtonSelectPv

    private val _latitudPv: MutableLiveData<Double> = MutableLiveData()
    private var latitudPv: MutableLiveData<Double> = _latitudPv

    private val _longitudPv: MutableLiveData<Double> = MutableLiveData()
    private val longitudPv: MutableLiveData<Double> = _longitudPv

    private val _buttonTextRegistro: MutableLiveData<String> = MutableLiveData()
    val buttonTextRegistro: MutableLiveData<String> = _buttonTextRegistro

    private val _developerModeEnabled = MutableLiveData<Boolean>()
    //val developerModeEnabled: LiveData<Boolean> = _developerModeEnabled

    private val _validacionVisita =
        MutableLiveData<ValidacionesVisitas.ValidacionInicioHoraResult>()

    fun getAddresses() {
        viewModelScope.launch(Dispatchers.IO) {
            val addresses = customerRepository.getAddresses()
            withContext(Dispatchers.Main) {
                _addressesList.clear()
                _addressesList.addAll(addresses)
            }
        }
    }


    fun getStoredAddresses(): List<OcrdItem> {
        return _addressesList
    }

    fun setIdOcrd(idOcrd: String, ocrd: String) {
        _idOcrd.value = idOcrd
        _ocrdName.value = ocrd
        _showButtonPv.value = true
        _buttonPvText.value = ocrd
    }

    @SuppressLint("SuspiciousIndentation")
    fun insertarVisita() {

         viewModelScope.launch {
            //AL LLAMAR insertarVisita() EJECUTA LA UBICACION ACTUAL DEL DISPOSITIVO
            val resultLocation= locationService.getUserLocation(context)
            var longitudUsuarioVal = resultLocation?.longitude ?: 0.0
            var latitudUsuarioVal = resultLocation?.latitude ?: 0.0

            val isAutomaticTimeZone = isAutomaticTimeZone(context)
            val isAutomaticDateTime = isAutomaticDateTime(context)
            val porceBateria = getBatteryPercentage(context)
            val idTurno = horariosUsuarioRepository.getIdTurno()
            //FUNCION SUSPENDIDA
            val rangoDistancia = 200
            /** ES PRIMERA VISITA EN FALSE AUTOMATICAMENTE, PORQUE EL AUDITOR NO DEPENDE DEL HORARIO **/
            _validacionVisita.value = horariosUsuarioRepository.validacionInicioHora(false)
            _developerModeEnabled.value = isDeveloperModeEnabled(context)

            if (_developerModeEnabled.value == true) {
                mostrarMensajeDialogo("Error, el modo desarrollador se encuentra habilitado.")
                LogUtils.insertLog(
                    logRepository,
                    LocalDateTime.now().toString(),
                    "Modo desarrollador activado",
                    "Se ha activado el modo desarrollador",
                    sharedPreferences.getUserId(),
                    sharedPreferences.getUserName()!!,
                    "REGISTRO DE VISITAS",
                    porceBateria
                )
            }
            else if (isAutomaticTimeZone == 0) {
                mostrarMensajeDialogo("Error, la zona horaria debe estar automatica")
                LogUtils.insertLog(
                    logRepository,
                    LocalDateTime.now().toString(),
                    "Zona horaria manual ",
                    "Zona horaria manual activada",
                    sharedPreferences.getUserId(),
                    sharedPreferences.getUserName()!!,
                    "REGISTRO DE VISITAS",
                    porceBateria
                )
            }
            // SI SE COLOCO LA HORA MANUAL
            else if (isAutomaticDateTime == 0) {
                mostrarMensajeDialogo("Error, la fecha y hora debe estar automatica")
                LogUtils.insertLog(
                    logRepository,
                    LocalDateTime.now().toString(),
                    "Hora y fecha manual ",
                    "Hora y fecha activado manualmente",
                    sharedPreferences.getUserId(),
                    sharedPreferences.getUserName()!!,
                    "REGISTRO DE VISITAS",
                    porceBateria
                )
            }
            else if (_validacionVisita.value?.respuestaVisita == 1)// SI MI HORARIO ESTA DENTRO DE LO PERMITIDO
            {
                transaccionVisita(
                    latitudUsuarioVal,
                    longitudUsuarioVal,
                    porceBateria,
                    idTurno,
                    rangoDistancia,
                    "NO"
                )
            }
            else //SI INTENTO MARCAR CON LLEGADA TARDIA
            {
                // VERIFICA SI TIENE ALGUN PERMISO PARA INICIAR VISITA FUERA DE HORA
                val permisoVisitaToken =
                    permisosVisitasRepository.verificarPermisoVisita("INICIOVISITA")

                if (!permisoVisitaToken) {
                    mostrarMensajeDialogo(_validacionVisita.value!!.mensaje)
                } else {
                    transaccionVisita(
                        latitudUsuarioVal,
                        longitudUsuarioVal,
                        porceBateria,
                        idTurno,
                        rangoDistancia,
                        "SI"
                    )
                }
            }
        }
    }

    fun transaccionVisita(
        latitudUsuarioVal: Double,
        longitudUsuarioVal: Double,
        porceBateria: Int,
        idTurno: Int,
        rangoDistancia: Int,
        llegadaTardia: String,
    ) {
        viewModelScope.launch {
            val visitaEstadoF = visitasRepository.getVisitaActiva("F")
            val secuenciaVisita = visitasRepository.getSecuenciaVisita()
            /** CASO PARA FINALIZAR LA VISITA INICIADA. ACTUALIZA EL REGISTRO DE FINALIZACION EN MODO MANUAL.*/
            if (visitaEstadoF != null) {
                val latitudPvVal = visitaEstadoF.latitudPV
                val longitudPvVal = visitaEstadoF.longitudPV
                //VERIFICA SI EL CIERRE ES NORMAL O FORZADO, SI TIENE TOKEN ENTONCES ES FORZADO.
                val permisoCierreForzado =
                    permisosVisitasRepository.verificarPermisoVisita("SALIDA_FUERA_PUNTO")
                val tipoCierreVar = if (permisoCierreForzado) "FORZADO" else "NORMAL"


                val metros = calculoMetrosPuntosGps(
                    latitudUsuarioVal,
                    longitudUsuarioVal,
                    latitudPvVal,
                    longitudPvVal
                )
                visitaEstadoF.apply {
                    createdAt = LocalDateTime.now().toString()
                    createdAtLong = System.currentTimeMillis()
                    latitudUsuario = latitudUsuarioVal
                    longitudUsuario = longitudUsuarioVal
                    porcentajeBateria = porceBateria
                    distanciaMetros = metros
                    tipoRegistro = "M"
                    tipoCierre = tipoCierreVar
                    pendienteSincro = "P"
                }

                if (metros > rangoDistancia) {
                    if (!permisoCierreForzado) {
                        mostrarMensajeDialogo("Estás fuera del área de cobertura. $metros metros del punto de venta.")
                    } else {
                        visitasRepository.updateVisita(visitaEstadoF)
                        _showDialog.value = true
                        _showButtonSelectPv.value = true
                        _mensajeDialog.value = "Visita finalizada con éxito"
                        _buttonTextRegistro.value = "Iniciar visita"

                    }
                } else {

                    visitasRepository.updateVisita(visitaEstadoF)
                    _showDialog.value = true
                    _showButtonSelectPv.value = true
                    _mensajeDialog.value = "Visita finalizada con éxito"
                    _buttonTextRegistro.value = "Iniciar visita"
                }
            }
            /**  CASO PARA INICIAR UNA VISITA, TAMBIEN SE REGISTRA LA FINALIZACION PERO COMO AUTOMATICO */
            else {
                val latitudPv = latitudPv.value!!
                val longitudPv = longitudPv.value!!

                val metros = calculoMetrosPuntosGps(
                    latitudUsuarioVal,
                    longitudUsuarioVal,
                    latitudPv,
                    longitudPv
                )

                if (idOcrd.value == null) {
                    mostrarMensajeDialogo("No se ha seleccionado el punto de venta")
                } else if (metros > rangoDistancia) {
                    mostrarMensajeDialogo("Estás fuera del área de cobertura. $metros metros del punto de venta.")
                } else {
                    val visitaApertura = crearVisitaEntity(
                        latitudUsuarioVal,
                        longitudUsuarioVal,
                        porceBateria,
                        metros,
                        "A",
                        "M",
                        latitudPv,
                        longitudPv,
                        ocrdName = ocrdName.value!!,
                        tardia = llegadaTardia,
                        idTurno = idTurno,
                        tipoCierre = "NORMAL",
                        exportado = false,
                        idUsuario = sharedPreferences.getUserId(),
                        idRol = 1,
                        idOcrd = idOcrd.value.toString(),
                        rol = "AUDITOR",
                        pendienteSincro = "P",
                        secuencia = secuenciaVisita,
                        id = System.currentTimeMillis()


                    )
                    val idPrimeraVisita = visitasRepository.insertVisita(visitaApertura)

                    if (idPrimeraVisita != -1L) {
                        val visitaCierre = crearVisitaEntity(
                            latitudUsuarioVal,
                            longitudUsuarioVal,
                            porceBateria,
                            metros,
                            estadoVisita = "F",
                            tipoRegistro = "A",
                            latitudPv,
                            longitudPv,
                            idA = idPrimeraVisita,
                            ocrdName = ocrdName.value!!,
                            tardia = llegadaTardia,
                            idTurno = idTurno,
                            tipoCierre = "NORMAL",
                            exportado = false,
                            idUsuario = sharedPreferences.getUserId(),
                            idRol = 1,
                            idOcrd = idOcrd.value.toString(),
                            rol = "AUDITOR",
                            pendienteSincro = "N",
                            secuencia = secuenciaVisita,
                            id = System.currentTimeMillis()

                        )
                        val idSegundaVisita = visitasRepository.insertVisita(visitaCierre)

                        if (idSegundaVisita != -1L) {
                            _showDialog.value = true
                            _showButtonSelectPv.value = false
                            _mensajeDialog.value = "Visita realizada con éxito"
                            _buttonTextRegistro.value = "Finalizar visita"
                            limpiarValores()
                        } else {
                            mostrarMensajeDialogo("Error al insertar la visita")
                        }
                    } else {
                        mostrarMensajeDialogo("Error al insertar la visita")
                    }
                }
            }
        }
    }


    private fun mostrarMensajeDialogo(mensaje: String) {
        _showDialog.value = true
        _mensajeDialog.value = mensaje
    }


    fun consultaVisitaActiva() {
        viewModelScope.launch {
            val visitaEstadoF = visitasRepository.getVisitaActiva("F")
            if (visitaEstadoF != null) {
                _showDialog.value = false
                _showButtonSelectPv.value = false
                _buttonTextRegistro.value = "Finalizar visita"
            } else {
                _showDialog.value = false
                _showButtonSelectPv.value = true
                _buttonTextRegistro.value = "Iniciar visita"
            }
        }


    }


    fun cerrarDialogMensaje() {
        _showDialog.value = false
    }

    fun setUbicacionPv(latitudPv: Double, longitudPv: Double) {
        _latitudPv.value = latitudPv
        _longitudPv.value = longitudPv
    }


    fun limpiarValores() {
// Restablecer los valores a su estado inicial
        idOcrd.value = null
        metros.value = null
        _showButtonPv.value = false
// _showDialog.value = false
//_mensajeDialog.value = ""
    }

}

