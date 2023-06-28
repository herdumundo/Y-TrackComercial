package com.example.y_trackcomercial.ui.marcacionPromotora

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.y_trackcomercial.model.entities.registro_entities.VisitasEntity
import com.example.y_trackcomercial.model.models.OcrdItem
import com.example.y_trackcomercial.repository.CustomerRepository
import com.example.y_trackcomercial.repository.VisitasRepository
import com.example.y_trackcomercial.services.battery.getBatteryPercentage
import com.example.y_trackcomercial.services.gps.calculoMetrosPuntosGps
import com.example.y_trackcomercial.services.time_zone.isAutomaticDateTime
import com.example.y_trackcomercial.services.time_zone.isAutomaticTimeZone
import com.example.y_trackcomercial.util.SharedPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import javax.inject.Inject


@HiltViewModel
class MarcacionPromotoraViewModel @Inject constructor(
    private val customerRepository: CustomerRepository,
    private val visitasRepository: VisitasRepository,
    private val sharedPreferences: SharedPreferences,
    private val context: Context
) : ViewModel() {

    private val _addressesList: MutableList<OcrdItem> = mutableListOf()

    val registrosConPendiente: LiveData<Int> = visitasRepository.getCantidadRegistrosPendientes()
    val OcrdNameLivedata: LiveData<String> = visitasRepository.getOcrdNameRepository()



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
    var latitudPv: MutableLiveData<Double> = _latitudPv

    private val _longitudPv: MutableLiveData<Double> = MutableLiveData()
    val longitudPv: MutableLiveData<Double> = _longitudPv

    private val _buttonTextRegistro: MutableLiveData<String> = MutableLiveData()
    val buttonTextRegistro: MutableLiveData<String> = _buttonTextRegistro


    fun getAddresses() {
        viewModelScope.launch(Dispatchers.IO) {
            val addresses = customerRepository.getAddresses()
            withContext(Dispatchers.Main) {
                _addressesList.clear()
                _addressesList.addAll(addresses)
            }
        }
    }
    init {
        getAddresses()
     }

    fun getStoredAddresses(): List<OcrdItem> {
        return _addressesList
    }

    fun setIdOcrd(idOcrd: String, ocrd: String ) {
        _idOcrd.value = idOcrd
        _ocrdName.value = ocrd
        _showButtonPv.value = true
        _buttonPvText.value = ocrd
    }

    fun insertarVisita(latitudUsuarioVal: Double, longitudUsuarioVal: Double) {
        //VALIDAR QUE NO SE PUEDA FINALIZAR VISITA SI ESTA A MAS DE 100 METROS.
        viewModelScope.launch {
            val porceBateria = getBatteryPercentage(context)
            val visitaEstadoF = visitasRepository.getVisitaActiva("F")
            val isAutomaticTimeZone = isAutomaticTimeZone(context)
            val isAutomaticDateTime = isAutomaticDateTime(context)

            if(isAutomaticTimeZone==0){
                mostrarMensajeDialogo("Error, la zona horaria debe estar automatica")
            }
            else if (isAutomaticDateTime==0) {
                mostrarMensajeDialogo("Error, la fecha y hora debe estar automatica")
            }

           else if (visitaEstadoF != null) {
                val latitudPvVal = visitaEstadoF.latitudPV
                val longitudPvVal = visitaEstadoF.longitudPV

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
                    pendienteSincro = "P"
                    tipoRegistro = "M"
                }
                if (metros > 100) {
                    mostrarMensajeDialogo("Estás fuera del área de cobertura. $metros metros del punto de venta.")
                } else {
                    visitasRepository.updateVisita(visitaEstadoF)
                    _showDialog.value = true
                    _showButtonSelectPv.value = true
                    _mensajeDialog.value = "Visita finalizada con éxito"
                    _buttonTextRegistro.value = "Iniciar visita"

                }
            } else {
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
                } else if (metros > 100) {
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
                        ocrdName=ocrdName.value!!
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
                            idA = idPrimeraVisita.toInt(),
                            pendienteSincro = "N",
                            ocrdName=ocrdName.value!!

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

    private fun crearVisitaEntity(
        latitudUsuario: Double,
        longitudUsuario: Double,
        porcentajeBateriaVal: Int,
        distanciaMetros: Int,
        estadoVisita: String,
        tipoRegistro: String,
        latitudPV: Double,
        longitudPV: Double,
        idA: Int = 0,
        pendienteSincro: String = "P",
        ocrdName:String
    ): VisitasEntity {
        return VisitasEntity(
            id = null,
            idUsuario = sharedPreferences.getUserId(),
            idOcrd = idOcrd.value.toString(),
            createdAt = LocalDateTime.now().toString(),
            createdAtLong = System.currentTimeMillis(),
            latitudUsuario = latitudUsuario,
            longitudUsuario = longitudUsuario,
            porcentajeBateria = porcentajeBateriaVal,
            distanciaMetros = distanciaMetros,
            pendienteSincro = pendienteSincro,
            estadoVisita = estadoVisita,
            idRol = 1,
            idA = idA,
            tipoRegistro = tipoRegistro,
            latitudPV = latitudPV,
            longitudPV = longitudPV,
            ocrdName=ocrdName

        )
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
