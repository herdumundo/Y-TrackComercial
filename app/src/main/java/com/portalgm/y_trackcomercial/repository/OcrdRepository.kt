package com.portalgm.y_trackcomercial.repository

import androidx.lifecycle.LiveData
import com.portalgm.y_trackcomercial.data.api.OcrdClient
import com.portalgm.y_trackcomercial.data.model.dao.CustomerDao
import com.portalgm.y_trackcomercial.data.model.dao.OcrdUbicacionesDao
import com.portalgm.y_trackcomercial.data.model.entities.OCRDEntity
import com.portalgm.y_trackcomercial.data.model.entities.OcrdUbicacionEntity
import com.portalgm.y_trackcomercial.data.model.models.OcrdItem
import com.portalgm.y_trackcomercial.util.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class CustomerRepository @Inject constructor(
    private val customerService: OcrdClient,
    private val customerDao:  CustomerDao,
    private val ocrdUbicacionesDao: OcrdUbicacionesDao ,
    private val sharedPreferences: SharedPreferences,

    ) {
     val customers: LiveData<List<OCRDEntity>> = customerDao.getCustomers()


    /* suspend fun fetchCustomers(): Int {
         return withContext(Dispatchers.IO) {
             val customers = customerService.getCustomers( sharedPreferences.getToken().toString())
             customerDao.eliminarOcrdTodos()


             customerDao.insertAllCustomers(customers)
             return@withContext getOcrdCount()
         }
     }*/
 suspend fun fetchCustomers(): Int {
     return withContext(Dispatchers.IO) {
         val customers = customerService.getCustomers(sharedPreferences.getToken().toString())

         val ocrdEntities = customers.map { apiResponse ->
             OCRDEntity(
                 id = apiResponse.id,
                 Address = apiResponse.Address,
                 CardCode = apiResponse.CardCode,
                 CardName = apiResponse.CardName,
                 CreditDisp = apiResponse.CreditDisp,
                 CreditLine = apiResponse.CreditLine,
                 Balance = apiResponse.Balance,
                 U_SIFENCIUDAD = apiResponse.U_SIFENCIUDAD,
                 U_DEPTOCOD = apiResponse.U_DEPTOCOD,
                 U_SIFENNCASA = apiResponse.U_SIFENNCASA,
                 correo = apiResponse.correo,
                 ListNum = apiResponse.ListNum,
                 LineNum = apiResponse.LineNum,
                 cardCode2 = apiResponse.cardCode2,
                 LicTradNum = apiResponse.LicTradNum,
                 PymntGroup = apiResponse.PymntGroup,
                 GroupNum = apiResponse.GroupNum,
                 STREET = apiResponse.STREET,
             )
         }

         val ocrdUbicacionEntities = customers.map { apiResponse ->
             OcrdUbicacionEntity(
                 id = apiResponse.OCRD_UBICACION.id,
                 idCab = apiResponse.OCRD_UBICACION.idCab,
                 latitud = apiResponse.OCRD_UBICACION.latitud,
                 longitud = apiResponse.OCRD_UBICACION.longitud
             )
         }
            customerDao.eliminarOcrdTodos()
            customerDao.insertAllCustomers(ocrdEntities)
            ocrdUbicacionesDao.deleteAllOCRD_UBICACION()
            ocrdUbicacionesDao.insertAllOcrdUbicaciones(ocrdUbicacionEntities)
         return@withContext getOcrdCount()
     }
 }
    suspend fun insertOcrd(list:List<OCRDEntity>) {
            customerDao.eliminarOcrdTodos()
            customerDao.insertAllCustomers(list)
    }

   suspend fun getDatosCliente():   List<OCRDEntity> {
        return customerDao.getDatosCliente()
    }
    fun getOcrdCount():  Int  {
        return customerDao.getCustomerCount()
    }

    fun getAddresses(): List< OcrdItem> {
        return customerDao.getOcrdAddresses()
    }

    fun getAllOcrd(): List<OCRDEntity> {
        return customerDao.getAllCustomers()
    }

}