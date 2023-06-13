package com.example.y_trackcomercial.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.y_trackcomercial.model.entities.OCRDEntity

@Dao
interface CustomerDao {
    @Query("SELECT * FROM ocrd   ")
    fun getCustomers(): LiveData<List<OCRDEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Transaction
    suspend fun insertCustomers(customers: List<OCRDEntity>)

    @Query("SELECT COUNT(*) FROM ocrd")
    fun getCustomerCount():  Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Transaction
    suspend fun insertAllCustomers(customers: List<OCRDEntity>)
}