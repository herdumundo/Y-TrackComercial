package com.portalgm.y_trackcomercial.repository

import androidx.lifecycle.LiveData
import com.portalgm.y_trackcomercial.data.api.OcrdClient
import com.portalgm.y_trackcomercial.data.model.entities.OCRDEntity
import com.portalgm.y_trackcomercial.data.model.models.OcrdItem
import com.portalgm.y_trackcomercial.util.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class CustomerRepository @Inject constructor(
    private val customerService: OcrdClient,
    private val customerDao: com.portalgm.y_trackcomercial.data.model.dao.CustomerDao,
    private val sharedPreferences: SharedPreferences,

    ) {
     val customers: LiveData<List<OCRDEntity>> = customerDao.getCustomers()
    suspend fun fetchCustomers(): Int {
        return withContext(Dispatchers.IO) {
            val customers = customerService.getCustomers( sharedPreferences.getToken().toString())
            customerDao.insertAllCustomers(customers)
            return@withContext getOcrdCount()
        }
    }

    suspend fun insertOcrd(list:List<OCRDEntity>) {
            customerDao.eliminarOcrdTodos()
            customerDao.insertAllCustomers(list)
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