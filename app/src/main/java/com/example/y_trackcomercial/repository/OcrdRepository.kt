package com.example.y_trackcomercial.repository

import androidx.lifecycle.LiveData
import com.example.y_trackcomercial.model.dao.CustomerDao
import com.example.y_trackcomercial.model.entities.OCRDEntity
 import com.example.y_trackcomercial.data.network.OcrdClient
import com.example.y_trackcomercial.model.models.OcrdItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class CustomerRepository @Inject constructor(
    private val customerService: OcrdClient,
    private val customerDao: CustomerDao
    ) {

    val customers: LiveData<List<OCRDEntity>> = customerDao.getCustomers()
    suspend fun fetchCustomers(): Int {
        return withContext(Dispatchers.IO) {
            val customers = customerService.getCustomers()
            customerDao.insertAllCustomers(customers)
            return@withContext getOcrdCount()
        }
    }

    fun getOcrdCount():  Int  {
        return customerDao.getCustomerCount()
    }

    fun getAddresses(): List<OcrdItem> {
        return customerDao.getOcrdAddresses()
    }

}