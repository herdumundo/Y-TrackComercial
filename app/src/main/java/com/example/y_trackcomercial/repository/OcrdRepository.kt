package com.example.y_trackcomercial.repository

import androidx.lifecycle.LiveData
import com.example.y_trackcomercial.data.api.OcrdClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class CustomerRepository @Inject constructor(
    private val customerService: OcrdClient,
    private val customerDao: com.example.y_trackcomercial.data.model.dao.CustomerDao
    ) {

    val customers: LiveData<List<com.example.y_trackcomercial.data.model.entities.OCRDEntity>> = customerDao.getCustomers()
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

    fun getAddresses(): List<com.example.y_trackcomercial.data.model.models.OcrdItem> {
        return customerDao.getOcrdAddresses()
    }

}