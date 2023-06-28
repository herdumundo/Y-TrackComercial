package com.example.y_trackcomercial.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.y_trackcomercial.model.entities.OCRDEntity
import com.example.y_trackcomercial.model.models.OcrdItem

@Dao
interface CustomerDao {
    @Query("SELECT * FROM ocrd   ")
    fun getCustomers(): LiveData<List<OCRDEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Transaction
    suspend fun insertCustomers(customers: List<OCRDEntity>)

    @Query("SELECT t1.id, t1.Address, t2.latitud,t2.longitud " +
            "FROM OCRD t1 " +
            "inner join OCRD_UBICACION t2 on t1.id=t2.idCab")
    fun getOcrdAddresses(): List<OcrdItem>


    @Query("SELECT COUNT(*) FROM ocrd")
    fun getCustomerCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Transaction
    suspend fun insertAllCustomers(customers: List<OCRDEntity>)
}