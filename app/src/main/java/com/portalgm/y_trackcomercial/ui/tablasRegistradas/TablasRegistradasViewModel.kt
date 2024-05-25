package com.portalgm.y_trackcomercial.ui.tablasRegistradas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.portalgm.y_trackcomercial.repository.OcrdUbicacionesRepository
import com.portalgm.y_trackcomercial.repository.CustomerRepository
import com.portalgm.y_trackcomercial.repository.HorariosUsuarioRepository
import com.portalgm.y_trackcomercial.repository.LotesListasRepository
import com.portalgm.y_trackcomercial.repository.OcrdOitmRepository
import com.portalgm.y_trackcomercial.repository.OitmRepository
import com.portalgm.y_trackcomercial.repository.RutasAccesosRepository
import com.portalgm.y_trackcomercial.repository.registroRepositories.logRepositories.AuditTrailRepository
import com.portalgm.y_trackcomercial.usecases.permisoVisita.CountRegistrosPermisosVisitaUseCase
import com.portalgm.y_trackcomercial.usecases.permisoVisita.ImportarPermisoVisitaUseCase
import com.portalgm.y_trackcomercial.usecases.ubicacionesPv.GetUbicacionesPvCountUseCase
import com.portalgm.y_trackcomercial.usecases.ubicacionesPv.ImportarUbicacionesPvUseCase
import com.portalgm.y_trackcomercial.usecases.ventas.listaPrecios.CountRegistrosListaPreciosUseCase
import com.portalgm.y_trackcomercial.usecases.ventas.listaPrecios.ImportarListaPreciosUseCase
import com.portalgm.y_trackcomercial.usecases.ventas.ordenVenta.CountOrdenVentaUseCase
import com.portalgm.y_trackcomercial.usecases.ventas.ordenVenta.ImportarOrdenVentaUseCase
import com.portalgm.y_trackcomercial.usecases.ventas.stockAlmacen.CountRegistrosStockAlmacenUseCase
import com.portalgm.y_trackcomercial.usecases.ventas.stockAlmacen.ImportarStockAlmacenUseCase
import com.portalgm.y_trackcomercial.usecases.ventas.vendedores.CountRegistrosVendedoresUseCase
import com.portalgm.y_trackcomercial.usecases.ventas.vendedores.ImportarVendedoresUseCase
import com.portalgm.y_trackcomercial.util.SharedPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel

class TablasRegistradasViewModel @Inject constructor(
    private val ocrdRepository: CustomerRepository,
    private val ocrdUbicacionesRepository: OcrdUbicacionesRepository,
    private val rutasAccesosRepository: RutasAccesosRepository,
    private val lotesListasRepository: LotesListasRepository,
    private val horariosUsuarioRepository: HorariosUsuarioRepository,
    private val auditTrailRepository: AuditTrailRepository,
    private val oitmRepository: OitmRepository,
    private val ocrdOitmRepository: OcrdOitmRepository,
    private val sharedPreferences: SharedPreferences,
    private val importarUbicacionesPvUseCase: ImportarUbicacionesPvUseCase,
    private val getUbicacionesPvCountUseCase: GetUbicacionesPvCountUseCase,
    private val importarPermisoVisitaUseCase: ImportarPermisoVisitaUseCase,
    private val countRegistrosPermisosVisitaUseCase: CountRegistrosPermisosVisitaUseCase,
    private val countRegistrosVendedoresUseCase: CountRegistrosVendedoresUseCase,
    private val importarVendedoresUseCase: ImportarVendedoresUseCase,
    private val importarListaPreciosUseCase: ImportarListaPreciosUseCase,
    private val countRegistrosListaPreciosUseCase: CountRegistrosListaPreciosUseCase,
    private val countRegistrosStockAlmacenUseCase: CountRegistrosStockAlmacenUseCase,
    private val importarStockAlmacenUseCase: ImportarStockAlmacenUseCase,
    private val importarOrdenVentaUseCase: ImportarOrdenVentaUseCase,
    private val countOrdenVentaUseCase: CountOrdenVentaUseCase,

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

    private val _oitmCount: MutableLiveData<Int> = MutableLiveData()
    val oitmCount: LiveData<Int> = _oitmCount

    private val _vendedorCount: MutableLiveData<Int> = MutableLiveData()
    val vendedorCount: LiveData<Int> = _vendedorCount

    private val _ocrdOitmCount: MutableLiveData<Int> = MutableLiveData()
    val ocrdOitmCount: LiveData<Int> = _ocrdOitmCount

    private val _loadingVendedorCount: MutableLiveData<Boolean> = MutableLiveData()
    val loadingVendedorCount: LiveData<Boolean> = _loadingVendedorCount

    private val _loadingListaPrecioCount: MutableLiveData<Boolean> = MutableLiveData()
    val loadingListaPrecioCount: LiveData<Boolean> = _loadingListaPrecioCount

    private val _loadingAlmacenStockCount: MutableLiveData<Boolean> = MutableLiveData()
    val loadingAlmacenStockCount: LiveData<Boolean> = _loadingAlmacenStockCount


    private val _loadingOcrdCount: MutableLiveData<Boolean> = MutableLiveData()
    val loadingOcrdCount: LiveData<Boolean> = _loadingOcrdCount

    private val _loadingUbicacionesCount: MutableLiveData<Boolean> = MutableLiveData()
    val loadingUbicacionesCount: LiveData<Boolean> = _loadingUbicacionesCount

    private val _loadingRutasAccesosCount: MutableLiveData<Boolean> = MutableLiveData()
    val loadingRutasAccesosCount: LiveData<Boolean> = _loadingRutasAccesosCount

    private val _loadingLotesListasCount: MutableLiveData<Boolean> = MutableLiveData()
    val loadingLotesListasCount: LiveData<Boolean> = _loadingLotesListasCount

    private val _loadingHorariosUsuarioCount: MutableLiveData<Boolean> = MutableLiveData()
    val loadingHorariosUsuarioCount: LiveData<Boolean> = _loadingHorariosUsuarioCount

    private val _loadingOitmCount: MutableLiveData<Boolean> = MutableLiveData()
    val loadingOitmCount: LiveData<Boolean> = _loadingOitmCount

    private val _loadingOcrdOitmCount: MutableLiveData<Boolean> = MutableLiveData()
    val loadingOcrdOitmCount: LiveData<Boolean> = _loadingOcrdOitmCount

    private val _loadingUbicacionesPvCount: MutableLiveData<Boolean> = MutableLiveData()
    val loadingUbicacionesPvCount: LiveData<Boolean> = _loadingUbicacionesPvCount

    private val _loadingordenVenta: MutableLiveData<Boolean> = MutableLiveData()
    val loadingordenVenta: LiveData<Boolean> = _loadingordenVenta

    private val _loadingonuevoNroFacturaCount: MutableLiveData<Boolean> = MutableLiveData()
    val loadingonuevoNroFacturaCount: LiveData<Boolean> = _loadingonuevoNroFacturaCount


    private val _ubicacionesPvCount: MutableLiveData<Int> = MutableLiveData()
    val ubicacionesPvCount: LiveData<Int> = _ubicacionesPvCount

    private val _permisoVisitaCount: MutableLiveData<Int> = MutableLiveData()
    val permisoVisitaCount: LiveData<Int> = _permisoVisitaCount

    private val _loadingpermisoVisitaCount: MutableLiveData<Boolean> = MutableLiveData()
    val loadingpermisoVisitaCount: LiveData<Boolean> = _loadingpermisoVisitaCount

    private val _listaPrecioCount: MutableLiveData<Int> = MutableLiveData()
    val listaPrecioCount: LiveData<Int> = _listaPrecioCount

    private val _almacenStockCount: MutableLiveData<Int> = MutableLiveData()
    val almacenStockCount: LiveData<Int> = _almacenStockCount

    private val _ordenVentaCount: MutableLiveData<Int> = MutableLiveData()
    val ordenVentaCount: LiveData<Int> = _ordenVentaCount

    private val _nuevoNroFacturaCount: MutableLiveData<Int> = MutableLiveData()
    val nuevoNroFacturaCount: LiveData<Int> = _nuevoNroFacturaCount


    fun getUserName(): String = sharedPreferences.getUserName().toString()
    fun getRol(): String = sharedPreferences.getRol().toString()
    fun getPasswordUserName(): String = sharedPreferences.getPasswordUserName().toString()
    fun getUserLogin(): String = sharedPreferences.getUserLogin().toString()
    fun getUserId(): Int = sharedPreferences.getUserId()

    fun actualizarDatos(tipo: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            when (tipo) {
                1 -> {
                    _loadingOcrdCount.value = true
                    _ocrdCount.value = ocrdRepository.fetchCustomers()
                    _loadingOcrdCount.value = false
                }
                2 -> {
                    _loadingUbicacionesCount.value = true
                    _ocrdUbicacionesCount.value = ocrdUbicacionesRepository.fetchOcrdUbicaciones()
                    _loadingUbicacionesCount.value = false
                }
                3 -> {
                    _loadingRutasAccesosCount.value = true
                    //rutasAccesosRepository.fetchRutasAccesos()
                    _loadingRutasAccesosCount.value = false
                }
                4 -> {
                    _loadingLotesListasCount.value = true
                    _lotesListasCount.value = lotesListasRepository.fetchLotesListas()
                    _loadingLotesListasCount.value = false
                }
                5 -> {
                    _loadingHorariosUsuarioCount.value = true
                    _horariosUsuarioCount.value = horariosUsuarioRepository.fetchHorariosUsuario(getUserId())
                    _loadingHorariosUsuarioCount.value = false
                }
                6 -> {
                    _loadingOitmCount.value = true
                    _oitmCount.value = oitmRepository.fetchOitm()
                    _loadingOitmCount.value = false
                }
                7 -> {
                    _loadingOcrdOitmCount.value = true
                    _ocrdOitmCount.value = ocrdOitmRepository.fetchOcrdOitm()
                    _loadingOcrdOitmCount.value = false
                }

                8 -> {
                    _loadingUbicacionesPvCount.value = true
                    _ubicacionesPvCount.value = importarUbicacionesPvUseCase.fetchUbicacionesPv()
                    _loadingUbicacionesPvCount.value = false
                }
                9 -> {
                    _loadingpermisoVisitaCount.value = true
                    _permisoVisitaCount.value =importarPermisoVisitaUseCase.importarPermisoVisita(getUserId())
                    _loadingpermisoVisitaCount.value = false
                }
                10 -> {
                    _loadingVendedorCount.value = true
                    _vendedorCount.value =importarVendedoresUseCase.Importar()
                    _loadingVendedorCount.value = false
                }
                11 -> {
                    _loadingListaPrecioCount.value = true
                    _listaPrecioCount.value =importarListaPreciosUseCase.Importar()
                    _loadingListaPrecioCount.value = false
                }
                12 -> {
                    _loadingAlmacenStockCount.value = true
                    _almacenStockCount.value =importarStockAlmacenUseCase.Importar()
                    _loadingAlmacenStockCount.value = false
                }
                13 -> {
                    _loadingordenVenta.value = true
                    _ordenVentaCount.value =importarOrdenVentaUseCase.Importar()
                    _loadingordenVenta.value = false
                }

            }
        }
    }


    fun getTablasRegistradas() {
        viewModelScope.launch(Dispatchers.IO) {
            val cantClientes = ocrdRepository.getOcrdCount()
            val cantUbicaciones = ocrdUbicacionesRepository.getOcrdUbicacionesCount()
            val cantRutasAccesos = rutasAccesosRepository.getRutasAccesosCount()
            val cantListasLotes = lotesListasRepository.getListasLotesCount()
            val cantHorariosUsuario = horariosUsuarioRepository.getTurnosHorariosCountRepository()
            val cantAuditTrail = auditTrailRepository.getAuditTrailCount()
            val cantOitm = oitmRepository.getCountOitm()
            val cantOcrdOitm = ocrdOitmRepository.getCountOcrdOitm()
            val cantubicacionesPvCount= getUbicacionesPvCountUseCase.getUbicacionesPvCount()
            val cantPermisoVisitaCount= countRegistrosPermisosVisitaUseCase.getCountPermisoVisitaCount()
            val cantVendedoresCount= countRegistrosVendedoresUseCase.contar()
            val cantListaPreciosCount= countRegistrosListaPreciosUseCase.contar()
            val cantStockAlmacen= countRegistrosStockAlmacenUseCase.contar()
            val cantOrdenVenta= countOrdenVentaUseCase.contar()


            withContext(Dispatchers.Main) {
                _ocrdCount.value                = cantClientes
                _ocrdUbicacionesCount.value     = cantUbicaciones
                _rutasAccesoCount.value         = cantRutasAccesos
                _lotesListasCount.value         = cantListasLotes
                _horariosUsuarioCount.value     = cantHorariosUsuario
                _auditTrailCount.value          = cantAuditTrail
                _oitmCount.value                = cantOitm
                _ocrdOitmCount.value            = cantOcrdOitm
                _ubicacionesPvCount.value       = cantubicacionesPvCount
                _permisoVisitaCount.value       = cantPermisoVisitaCount
                _vendedorCount.value            = cantVendedoresCount
                _listaPrecioCount.value         = cantListaPreciosCount
                _almacenStockCount.value        = cantStockAlmacen
                _ordenVentaCount.value          = cantOrdenVenta

            }
        }
    }

    init {
        getTablasRegistradas()
    }

}