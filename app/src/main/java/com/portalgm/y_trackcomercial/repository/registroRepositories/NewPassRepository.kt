package com.portalgm.y_trackcomercial.repository.registroRepositories

 import android.util.Log
import com.portalgm.y_trackcomercial.data.api.exportaciones.ExportacionNewPassApiClient
 import com.portalgm.y_trackcomercial.data.api.request.NuevoPass
 import com.portalgm.y_trackcomercial.data.model.dao.registroDaos.NewPassDao
import com.portalgm.y_trackcomercial.data.model.entities.registro_entities.NewPassEntity
import com.portalgm.y_trackcomercial.util.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewPassRepository @Inject constructor
    (
        private val newPassDao: NewPassDao,
        private val sharedPreferences: SharedPreferences,
       private val exportacionNewPassApiClient: ExportacionNewPassApiClient // Paso 1: Agregar el ApiClient al constructor
    ) {
    suspend fun insertNewPass(newPassEntity: NewPassEntity): Long {

        return withContext(Dispatchers.IO) {
            newPassDao.eliminarTodos()
            return@withContext newPassDao.insertNewPass(newPassEntity)
        }

    }

    suspend fun getCount(): Int {
        return newPassDao.getCountPendientes()
    }

    suspend fun getAllLotesNuevasUbicaciones(): NuevoPass {

        val  Entity = newPassDao.getAllNuevoPass()
        return   NuevoPass(
                idUsuario = Entity[0].idUsuario,
                clave =  Entity[0].clave )
     }



        suspend fun exportarDatos(lotes: NuevoPass) {
            try {
                val apiResponse = exportacionNewPassApiClient.uploadData(lotes,sharedPreferences.getToken().toString())
                // Puedes también manejar la respuesta de la API según el campo "tipo" del ApiResponse
                if (apiResponse.tipo == 0) {
                    newPassDao.updateExportadoCerrado()
                }
                Log.i("MensajeTest", apiResponse.msg)
            } catch (e: Exception) {
                Log.i("Mensaje", e.toString())
            }
        }

}