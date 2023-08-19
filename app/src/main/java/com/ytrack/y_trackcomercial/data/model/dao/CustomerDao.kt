package com.ytrack.y_trackcomercial.data.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.ytrack.y_trackcomercial.data.model.models.OcrdItem

@Dao
interface CustomerDao {
    @Query("SELECT * FROM ocrd   ")
    fun getCustomers(): LiveData<List<com.ytrack.y_trackcomercial.data.model.entities.OCRDEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Transaction
    suspend fun insertCustomers(customers: List<com.ytrack.y_trackcomercial.data.model.entities.OCRDEntity>)

    @Query("SELECT t1.id, t1.Address, t2.latitud,t2.longitud " +
            "FROM OCRD t1 " +
            "inner join OCRD_UBICACION t2 on t1.id=t2.idCab order by t1.Address asc ")
    fun getOcrdAddresses(): List<OcrdItem>


    @Query("SELECT COUNT(*) FROM ocrd")
    fun getCustomerCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Transaction
    suspend fun insertAllCustomers(customers: List<com.ytrack.y_trackcomercial.data.model.entities.OCRDEntity>)
}