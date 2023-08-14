package com.ytrack.y_trackcomercial.data.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PermisosVisitasDao {

    @Query("SELECT token FROM PERMISOS_VISITAS WHERE idEstado = 1 and tipoPermiso=:tipoPermiso ORDER BY id DESC LIMIT 1")
    suspend fun getToken(tipoPermiso : String ): String

    @Query("SELECT count(*) FROM PERMISOS_VISITAS WHERE idEstado = 1 ")
      fun getPermisoVisitaCount(): Int


    @Insert(onConflict =  OnConflictStrategy.REPLACE)
    suspend fun insertPermisoVisita(permisos: List<com.ytrack.y_trackcomercial.data.model.entities.PermisosVisitasEntity>)

    @Query("select count(*) from movimientos where idVisitas in (select idA from visitas where pendienteSincro='N') ")
    suspend fun getPermisoCierreVisitaInventarioOk(): Int

    @Query("select count(*) from visitas where pendienteSincro='N' ")
    suspend fun getCierrePendiente(): Int

}
