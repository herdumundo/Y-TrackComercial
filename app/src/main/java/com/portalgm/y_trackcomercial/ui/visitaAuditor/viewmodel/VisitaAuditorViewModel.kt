package com.portalgm.y_trackcomercial.ui.visitaAuditor.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.portalgm.y_trackcomercial.data.api.request.EnviarVisitasRequest
import com.portalgm.y_trackcomercial.data.model.entities.registro_entities.VisitasEntity
import com.portalgm.y_trackcomercial.data.model.models.OcrdItem
import com.portalgm.y_trackcomercial.repository.CustomerRepository
import com.portalgm.y_trackcomercial.repository.HorariosUsuarioRepository
import com.portalgm.y_trackcomercial.repository.PermisosVisitasRepository
import com.portalgm.y_trackcomercial.repository.registroRepositories.VisitasRepository
import com.portalgm.y_trackcomercial.repository.registroRepositories.logRepositories.AuditTrailRepository
import com.portalgm.y_trackcomercial.repository.registroRepositories.logRepositories.LogRepository
import com.portalgm.y_trackcomercial.services.battery.getBatteryPercentage
import com.portalgm.y_trackcomercial.services.developerMode.isDeveloperModeEnabled
import com.portalgm.y_trackcomercial.services.gps.calculoMetrosPuntosGps
 import com.portalgm.y_trackcomercial.services.gps.locatioGoogleMaps.obtenerUbicacionGPSActual
import com.portalgm.y_trackcomercial.services.gps.locatioGoogleMaps.openGoogleMapsWithMyLocation
import com.portalgm.y_trackcomercial.services.gps.locationLocal.LocationListenerTest
import com.portalgm.y_trackcomercial.services.gps.locationLocal.insertRoomLocation
import com.portalgm.y_trackcomercial.services.gps.servicioGMS.LocationCallBacks
import com.portalgm.y_trackcomercial.services.time_zone.isAutomaticDateTime
import com.portalgm.y_trackcomercial.services.time_zone.isAutomaticTimeZone
import com.portalgm.y_trackcomercial.util.SharedPreferences
import com.portalgm.y_trackcomercial.util.ValidacionesVisitas
import com.portalgm.y_trackcomercial.util.logUtils.LogUtils
import com.portalgm.y_trackcomercial.util.registrosVisitas.crearVisitaEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import javax.inject.Inject
import com.portalgm.y_trackcomercial.services.gps.servicioGMS.LocationService
import com.portalgm.y_trackcomercial.usecases.exportacionVisitas.EnviarVisitasPendientesUseCase
import com.portalgm.y_trackcomercial.usecases.exportacionVisitas.GetVisitasPendientesUseCase
import com.portalgm.y_trackcomercial.usecases.permisoVisita.ImportarPermisoVisitaUseCase
import com.portalgm.y_trackcomercial.util.SharedData

@HiltViewModel
class VisitaAuditorViewModel @Inject constructor(
    private val customerRepository: CustomerRepository,
    private val visitasRepository: VisitasRepository,
    private val sharedPreferences: SharedPreferences,
    private val permisosVisitasRepository: PermisosVisitasRepository,
    private val horariosUsuarioRepository: HorariosUsuarioRepository,
    private val logRepository: LogRepository,
    private val context: Context,
    private val locationService:  LocationService,
    private val auditTrailRepository: AuditTrailRepository,
    private val getVisitasPendientesUseCase: GetVisitasPendientesUseCase,
    private val enviarVisitasPendientesUseCase: EnviarVisitasPendientesUseCase,
    private val importarPermisoVisitaUseCase: ImportarPermisoVisitaUseCase,


    ) : ViewModel() {

   // private val  locationService:LocationService= LocationService()
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
   var latitudPv: MutableLiveData<Double> = _latitudPv

    private val _longitudPv: MutableLiveData<Double> = MutableLiveData()
      var longitudPv: MutableLiveData<Double> = _longitudPv

    private val _buttonTextRegistro: MutableLiveData<String> = MutableLiveData()
    val buttonTextRegistro: MutableLiveData<String> = _buttonTextRegistro

    private val _permitirUbicacion = MutableLiveData<Boolean>()
    val permitirUbicacion: LiveData<Boolean> = _permitirUbicacion

    private val _ubicacionLoading = MutableLiveData<Boolean>()
    val ubicacionLoading: LiveData<Boolean> = _ubicacionLoading

    private val _developerModeEnabled = MutableLiveData<Boolean>()
    //val developerModeEnabled: LiveData<Boolean> = _developerModeEnabled

    private val _validacionVisita =  MutableLiveData<ValidacionesVisitas.ValidacionInicioHoraResult>()

    private val _latitudUsuario: MutableLiveData<Double> = MutableLiveData()
    var latitudUsuario: MutableLiveData<Double> = _latitudUsuario

    private val _longitudUsuario: MutableLiveData<Double> = MutableLiveData()
    val longitudUsuario: MutableLiveData<Double> = _longitudUsuario

    private val _cuadroLoadingMensaje = MutableLiveData<String>()
    val cuadroLoadingMensaje: LiveData<String> = _cuadroLoadingMensaje

    private val _cuadroLoading = MutableLiveData<Boolean>()
    val cuadroLoading: LiveData<Boolean> = _cuadroLoading


    val sharedData = SharedData.getInstance()

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


    fun inicializarValores(){
        _permitirUbicacion.value=true
    }

    fun obtenerUbicacion(){
        _cuadroLoading.value=true
        _cuadroLoadingMensaje.value="Obteniendo ubicacion actual..."
        viewModelScope.launch {
            locationService.startLocationUpdates()
            locationService.setLocationCallback(object : LocationCallBacks {
                override fun onLocationUpdated(location: Location) {
                    // Actualiza la latitud y longitud con la ubicación actualizada
                    _latitudUsuario.value=location.latitude
                    _longitudUsuario.value=location.longitude
                }
            })
            delay(8000) // 10 minutos en milisegundos
            locationService.stopLocationUpdates()
            _cuadroLoading.value=false
        }

    }
 @SuppressLint("SuspiciousIndentation")
 fun insertarVisita() {
     if(_permitirUbicacion.value!!){

         _buttonTextRegistro.value = "Procesando..."
         _permitirUbicacion.value=false

        /* locationListener = LocationListenerTest()
         val locationManager =  context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
         obtenerUbicacionGPSActual(locationListener,context,locationManager)*/
         locationService.startLocationUpdates()

         //VALIDAR QUE NO SE PUEDA FINALIZAR VISITA SI ESTA A MAS DE 100 METROS.
         viewModelScope.launch {
             //AL LLAMAR insertarVisita() EJECUTA LA UBICACION ACTUAL DEL DISPOSITIVO

                  locationService.setLocationCallback(object : LocationCallBacks {
                     override fun onLocationUpdated(location: Location) {
                         // Actualiza la latitud y longitud con la ubicación actualizada
                         _latitudUsuario.value=location.latitude
                         _longitudUsuario.value=location.longitude
                     }
                 })
             delay(8000)

           /*  val resultLocation= locationService.getUserLocation(context)
             var longitudUsuarioVal = resultLocation?.longitude ?: 0.0
             var latitudUsuarioVal = resultLocation?.latitude ?: 0.0*/

             val isAutomaticTimeZone = isAutomaticTimeZone(context)
             val isAutomaticDateTime = isAutomaticDateTime(context)
             val porceBateria = getBatteryPercentage(context)
             val idTurno = horariosUsuarioRepository.getIdTurno()
             _developerModeEnabled.value = isDeveloperModeEnabled(context)

             //FUNCION SUSPENDIDA
             //val esPrimeraVisita = horariosUsuarioRepository.esPrimeraVisitaTurno(idTurno)

             val rangoDistancia = 200

             /**HACER QUE SI EXISTE UN CIERRE PENDIENTE, NO ENTRE EN "ES PRIMERA VISITA.
              * ESTO PORQUE PUEDE EXISTIR UNA VISITA DEL DIA ANTERIOR QUE NO SE FINALIZO, ENTONCES AL SIGUIENTE DIA,
              * SIEMPRE COMIENZA COMO QUE ES PRIMERA VISITA." */
             val visitaEstadoF = visitasRepository.getVisitaActiva("F")

             // val cierrePendiente = verificarCierrePendienteUseCase.verificarCierrePendiente()
              _validacionVisita.value = horariosUsuarioRepository.validacionInicioHora(false)

             if (_developerModeEnabled.value==true)
             {
                 mostrarMensajeDialogo("Error, el modo desarrollador se encuentra habilitado.")
                 LogUtils.insertLog(logRepository, LocalDateTime.now().toString(), "Modo desarrollador activado", "Se ha activado el modo desarrollador", sharedPreferences.getUserId(), sharedPreferences.getUserName()!!, "REGISTRO DE VISITAS",porceBateria)
                 _buttonTextRegistro.value = "Reintentar"
             } // SI SE COLOCO LA ZONA HORARIA MANUAL
             else if (isAutomaticTimeZone == 0)
             {
                 mostrarMensajeDialogo("Error, la zona horaria debe estar automatica")
                 LogUtils.insertLog(logRepository, LocalDateTime.now().toString(), "Zona horaria manual ", "Zona horaria manual activada", sharedPreferences.getUserId(), sharedPreferences.getUserName()!!, "REGISTRO DE VISITAS",porceBateria)
                 _buttonTextRegistro.value = "Reintentar"

             }
             // SI SE COLOCO LA HORA MANUAL
             else if (isAutomaticDateTime == 0)
             {
                 mostrarMensajeDialogo("Error, la fecha y hora debe estar automatica")
                 LogUtils.insertLog(logRepository, LocalDateTime.now().toString(), "Hora y fecha manual ", "Hora y fecha activado manualmente", sharedPreferences.getUserId(), sharedPreferences.getUserName()!!, "REGISTRO DE VISITAS",porceBateria)
                 _buttonTextRegistro.value = "Reintentar"
             }
             else
             {//COMIENZA EL INTENTO PARA REGISTRO.
                 val tipoRespuestaValidacion=_validacionVisita.value?.respuestaVisita
                 val mensajeValidacion=_validacionVisita.value?.mensaje
                 val permisoVisitaToken = permisosVisitasRepository.verificarPermisoVisita("INICIOVISITA")
                 val llegadaTardia= if  (tipoRespuestaValidacion==1)  "NO" else "SI"
                 transaccionVisita(
                     _latitudUsuario.value?:0.0,
                     _longitudUsuario.value?:0.0,
                     porceBateria,
                     idTurno,
                     rangoDistancia,
                     llegadaTardia,
                     visitaEstadoF,
                     tipoRespuestaValidacion,
                     mensajeValidacion,
                     permisoVisitaToken )
             }
             locationService.stopLocationUpdates()
             _permitirUbicacion.value=true
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
     visitaEstadoF: VisitasEntity?,
     // validacionVisita: Int?,
     tipoRespuestaValidacion: Int?,
     mensajeValidacion: String?,
     permisoVisitaToken: Boolean
 ) {
     viewModelScope.launch {
         //val visitaEstadoF = visitasRepository.getVisitaActiva("F")
         //val visitaEstadoF = visitasRepository.getVisitaActiva("F")
         val secuenciaVisita = visitasRepository.getSecuenciaVisita()

         /** CASO PARA FINALIZAR LA VISITA INICIADA. ACTUALIZA EL REGISTRO DE FINALIZACION EN MODO MANUAL.*/
         if (visitaEstadoF != null) {
             val latitudPvVal = visitaEstadoF.latitudPV
             val longitudPvVal = visitaEstadoF.longitudPV
             //VERIFICA SI EL CIERRE ES NORMAL O FORZADO, SI TIENE TOKEN ENTONCES ES FORZADO.
             var permisoCierreForzado =  permisosVisitasRepository.verificarPermisoVisita("SALIDA_FUERA_PUNTO")
             val tipoCierreVar = if (permisoCierreForzado) "FORZADO" else "NORMAL"
           //  var inventarioExistente = verificarInventarioCierreVisitaUseCase.verificarInventarioExistente()

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
                 tipoCierre = tipoCierreVar
                 pendienteSincro="P"
                 exportado=false
             }
             //CASO PARA FINALIZAR CIERRE FUERA DEL PUNTO
             if (metros > rangoDistancia) {
                 if (!permisoCierreForzado) {
                     mostrarMensajeDialogo("Estás fuera del área de cobertura. $metros metros del punto de venta.")
                     _buttonTextRegistro.value = "Finalizar visita"
                 }
                 else
                 {
                     visitasRepository.updateVisita(visitaEstadoF)
                     _showDialog.value = true
                     _showButtonSelectPv.value = true
                     _mensajeDialog.value = "Visita finalizada con éxito"
                     _buttonTextRegistro.value = "Iniciar visita"
                     exportarPendientes()
                     finalizarVariablesGlobalesVisita(latitudUsuarioVal,longitudUsuarioVal)
                 }
             }

             else {
                 visitasRepository.updateVisita(visitaEstadoF)
                 _showDialog.value = true
                 _showButtonSelectPv.value = true
                 _mensajeDialog.value = "Visita finalizada con éxito"
                 _buttonTextRegistro.value = "Iniciar visita"
                 exportarPendientes()
                 finalizarVariablesGlobalesVisita(latitudUsuarioVal,longitudUsuarioVal)

             }
         }
         /**  CASO PARA INICIAR UNA VISITA, TAMBIEN SE REGISTRA LA FINALIZACION PERO COMO AUTOMATICO */
         else {
             /** Estados de respuestas.
              *
              *  1 = Permite iniciar visita tras la validaciones, o permite porque no es la primera visita del turno.
              *
              *  2 = Llegada tarde.
              *
              *  3 = Muy temprano para iniciar visita.
              *
              *  4 = No existen turnos para el horario en que se intenta iniciar visita.
              * */
             if (/*(tipoRespuestaValidacion == 2 && !permisoVisitaToken) ||*/ tipoRespuestaValidacion in listOf(3, 4)) {
                 mostrarMensajeDialogo(mensajeValidacion!!)
                 _buttonTextRegistro.value = "Iniciar visita"

                 return@launch
             }

             //val latitudPv = latitudPv.value!!
             val latitudPv = latitudPv.value ?: 0.0

             val longitudPv = longitudPv.value ?: 0.0
             val metros = calculoMetrosPuntosGps(
                 latitudUsuarioVal,
                 longitudUsuarioVal,
                 latitudPv,
                 longitudPv
             )
             if (idOcrd.value == null) {
                 mostrarMensajeDialogo("No se ha seleccionado el punto de venta")
                 _buttonTextRegistro.value = "Iniciar visita"

             } else if (metros > rangoDistancia) {
                 mostrarMensajeDialogo("Estás fuera del área de cobertura. $metros metros del punto de venta.")
                 _buttonTextRegistro.value = "Iniciar visita"
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
                     secuencia=secuenciaVisita,
                     id=System.currentTimeMillis()

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
                         secuencia=secuenciaVisita,
                         id=System.currentTimeMillis()
                     )
                     val idSegundaVisita = visitasRepository.insertVisita(visitaCierre)
                     if (idSegundaVisita != -1L) {
                         _showDialog.value = true
                         _showButtonSelectPv.value = false
                         _mensajeDialog.value = "Visita realizada con éxito"
                         _buttonTextRegistro.value = "Finalizar visita"
                         exportarPendientes()
                         iniciarVariablesGlobalesVisita(idPrimeraVisita,latitudPv,longitudPv)
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



/*
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
*/

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

    suspend fun iniciarVariablesGlobalesVisita(idPrimeraVisita : Long,latitudPv : Double,longitudPv: Double){
        sharedData.idVisitaGlobal.value=idPrimeraVisita
        sharedData.latitudPV.value=latitudPv
        sharedData.longitudPV.value=longitudPv
        sharedData.fechaLongGlobal.value=System.currentTimeMillis()
        sharedData.tiempo.value=0
        insertRoomLocation(
            latitudPv, longitudPv,
            context, sharedPreferences, auditTrailRepository,"INICIO VISITA"
        )
    }

    suspend fun finalizarVariablesGlobalesVisita( latitudPv : Double,longitudPv: Double){

        sharedData.fechaLongGlobal.value=System.currentTimeMillis()
        insertRoomLocation(
            latitudPv, longitudPv,
            context, sharedPreferences, auditTrailRepository,"FINAL VISITA"
        )
        sharedData.idVisitaGlobal.value=0

    }
    private fun exportarPendientes(){
        viewModelScope.launch {
            try {
                val visitasPendientes =  getVisitasPendientesUseCase.getVisitasPendientes()
                val enviarVisitasRequest = EnviarVisitasRequest(visitasPendientes)
                enviarVisitasPendientesUseCase.enviarVisitasPendientes(  enviarVisitasRequest  )
            } catch (e: Exception) {
                Log.i("Mensaje",e.toString())
            }
        }
    }

    fun abrirmapaGoogleMaps(context:Context){
        openGoogleMapsWithMyLocation(context)
    }
    fun obtenerPermisos(){
        _cuadroLoadingMensaje.value="Obteniendo permisos..."

        try {
            viewModelScope.launch(Dispatchers.Main) {
                _cuadroLoading.value=true
                importarPermisoVisitaUseCase.importarPermisoVisita(sharedPreferences.getUserId())
                _cuadroLoading.value = false
            }
        }catch (e : Exception){
            _cuadroLoading.value = false
        }
    }
}


