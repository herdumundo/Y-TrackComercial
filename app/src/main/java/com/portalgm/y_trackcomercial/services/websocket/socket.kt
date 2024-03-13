package com.portalgm.y_trackcomercial.services.websocket

import android.util.Log
import com.portalgm.y_trackcomercial.BuildConfig
import com.portalgm.y_trackcomercial.data.model.entities.LotesListasEntity
import com.portalgm.y_trackcomercial.data.model.entities.OCRDEntity
import com.portalgm.y_trackcomercial.data.model.entities.OcrdOitmEntity
import com.portalgm.y_trackcomercial.data.model.entities.OcrdUbicacionEntity
import com.portalgm.y_trackcomercial.data.model.entities.OitmEntity
import com.portalgm.y_trackcomercial.repository.CustomerRepository
import com.portalgm.y_trackcomercial.repository.LotesListasRepository
import com.portalgm.y_trackcomercial.repository.OcrdOitmRepository
import com.portalgm.y_trackcomercial.repository.OcrdUbicacionesRepository
import com.portalgm.y_trackcomercial.repository.OitmRepository
import com.portalgm.y_trackcomercial.util.SharedData
import com.portalgm.y_trackcomercial.util.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.InputStreamReader
import java.util.Calendar
import java.util.zip.GZIPInputStream
import javax.inject.Inject

class PieSocketListener @Inject constructor(
    private val ocrdRepository: CustomerRepository,
    private val lotesListasRepository: LotesListasRepository,
    private val ocrdUbicacionesRepository: OcrdUbicacionesRepository,
    private val ocrdOitmRepository: OcrdOitmRepository,
    private val oitmRepository: OitmRepository,
    private val sharedPreferences: SharedPreferences
) : WebSocketListener() {
    private var webSocket: WebSocket? = null
    private var serverUrl = BuildConfig.BASE_URL_WEB_SOCKET // Reemplaza con la URL de tu WebSocket
    private var reconnectAttempts = 0
    private var maxReconnectAttempts = 100000 // Número máximo de intentos de reconexión
    private var reconnectIntervalMillis = 5000L // Intervalo de reconexión en milisegundos
    private var reconnectJob: Job? = null
    private var connected = false
    val sharedData = SharedData.getInstance()

    override fun onOpen(webSocket: WebSocket, response: Response) {
        GlobalScope.launch(Dispatchers.Main) {
            sharedData.webSocketConectado.value=true
        }
        Log.d("PieSocket", "Connected")
        connected = true // Establece el estado de conexión a verdadero
        this.webSocket = webSocket
        reconnectAttempts = 0 // Restablece el contador de intentos de reconexión
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        try {
          //  var ping = calculateSizeInKB(text.toByteArray())
            enviarCoordenadas("")
        } catch (e: Exception) {
            output("Errores: ${e.message}")
        }
    }
    fun calculateSizeInKB(jsonString: ByteArray): Double {
        // Convierte la cadena JSON a bytes usando UTF-8
        //val bytes = jsonString.toByteArray()
        // Calcula el tamaño en KB dividiendo la longitud en bytes por 1024
        val sizeInKB = jsonString.size.toDouble() / 1024.0
        return sizeInKB
    }
    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        // Se ha recibido un mensaje binario
        val byteArray = bytes.toByteArray()
     //   var peso=calculateSizeInKB(bytes.toByteArray())
        try {
            // Comprueba si los bytes están comprimidos (debes ajustar esto según el formato del mensaje)
            if (isGzipped(byteArray)) {
                // Los bytes están comprimidos
                val gzipInputStream = GZIPInputStream(ByteArrayInputStream(byteArray))
                val bufferedReader = BufferedReader(InputStreamReader(gzipInputStream, "UTF-8"))
                val jsonString = bufferedReader.readText()
                val jsonObject = JSONObject(jsonString)

              /*  if (jsonObject.has("ocrd")) {
                    insertarOcrd(jsonObject.getJSONArray("ocrd"))
                }*/
                if (jsonObject.has("lotesLista")) {
                    insertarLotesListas(jsonObject.getJSONArray("lotesLista"))
                }
              /*  if (jsonObject.has("ubicaciones")) {
                    insertarOcrdUbicacion(jsonObject.getJSONArray("ubicaciones"))
                }*/
                if (jsonObject.has("oitmXocrd")) {
                    insertarOitmXocrd(jsonObject.getJSONArray("oitmXocrd"))
                }
                if (jsonObject.has("oitm")) {
                    insertarOitm(jsonObject.getJSONArray("oitm"))
                }
            } else {
                // Los bytes no están comprimidos
            }
        } catch (e: Exception) {
            println(e.message)
        }
    }
    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        connected = false
        GlobalScope.launch(Dispatchers.Main) {
            sharedData.webSocketConectado.value=false
        }
        webSocket.close(NORMAL_CLOSURE_STATUS, null)
        output("Closing: $code / $reason")
    }
    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        val errorMessage = "Error: ${t.message}"
        output(errorMessage)
        GlobalScope.launch(Dispatchers.Main) {
            sharedData.webSocketConectado.value=false
        }
        connected = false
        val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        if (currentHour in 6..19) {   // Verifica si la hora actual está entre las 6 am y las 7 pm
        if (reconnectAttempts < maxReconnectAttempts) {
            reconnectJob = GlobalScope.launch(Dispatchers.IO) {
                output("Reconnecting...")
                // Espera el tiempo especificado antes de intentar la reconexión
                delay(reconnectIntervalMillis)//SON 5 SEGUNDOS
                // Cancela la conexión WebSocket actual
                webSocket.cancel()
                // Crea una nueva conexión
                connectToServer(serverUrl)
                reconnectAttempts++
            }
        } else {
            output("Reached max reconnection attempts. Connection failed.")
        }
        }
    }

    fun enviarCoordenadas(message: String) {
        webSocket?.send(message)
    }

    fun isConnected(): Boolean {
        return connected
    }


    private fun isGzipped(data: ByteArray): Boolean {
        // Comprueba si los datos comienzan con los bytes de encabezado GZIP
        return data.size >= 2 && data[0] == 0x1F.toByte() && data[1] == 0x8B.toByte()
    }
    fun connectToServer(url: String): WebSocket {
        val request = okhttp3.Request.Builder()
            .url(url)
            .addHeader("client-type", "ANDROID") // Agrega la cabecera personalizada aquí
            .addHeader("user-id", sharedPreferences.getUserId().toString()) // Agrega el ID del usuario como un encabezado personalizado
            .build()
        val client = okhttp3.OkHttpClient.Builder().build()
        return client.newWebSocket(request, this)
    }
    fun closeWebSocket() {
        webSocket?.close(1000, "Cierre normal") // Cierra el WebSocket con código 1000 y mensaje de cierre "Cierre normal"
        webSocket = null // Establece la referencia del WebSocket a nulo
    }

    companion object {
        private const val NORMAL_CLOSURE_STATUS = 1000
    }
    fun output(text: String?) {
        Log.d("PieSocket", text!!)
    }
    fun insertarOcrd(ocrdArray: JSONArray) {
        val ocrdList = mutableListOf<OCRDEntity>()

        for (i in 0 until ocrdArray.length()) {
            val ocrdObject = ocrdArray.getJSONObject(i)
            val ocrdEntity = OCRDEntity(
                ocrdObject.getString("id"),
                ocrdObject.getString("Address"),
                ocrdObject.getString("CardCode"),
                ocrdObject.getString("CardName")
            )
            ocrdList.add(ocrdEntity)
        }
        GlobalScope.launch(Dispatchers.IO) {
            ocrdRepository.insertOcrd(ocrdList)
        }
    }
    fun insertarLotesListas(array: JSONArray) {
        val lista = mutableListOf<LotesListasEntity>()

        for (i in 0 until array.length()) {
            val ocrdObject = array.getJSONObject(i)
            val ocrdEntity = LotesListasEntity(
                ocrdObject.getString("id"),
                ocrdObject.getString("FechaProd"),
                ocrdObject.getString("ItemCode"),
                ocrdObject.getString("ItemName"),
                ocrdObject.getString("CodeBars"),
                ocrdObject.getString("FCP")
            )
            lista.add(ocrdEntity)

        }
        GlobalScope.launch(Dispatchers.IO) {
            lotesListasRepository.insertLotesListas(lista)
        }
    }
    fun insertarOcrdUbicacion(array: JSONArray) {
        val lista = mutableListOf<OcrdUbicacionEntity>()

        for (i in 0 until array.length()) {
            val Object = array.getJSONObject(i)
            val Entity = OcrdUbicacionEntity(
                Object.getInt("id"),
                Object.getString("idCab"),
                Object.getString("latitud"),
                Object.getString("longitud")
            )
            lista.add(Entity)

        }
        GlobalScope.launch(Dispatchers.IO) {
            ocrdUbicacionesRepository.insertOcrdUbicaciones(lista)
        }
    }

    fun insertarOitmXocrd(array: JSONArray) {
        val lista = mutableListOf<OcrdOitmEntity>()

        for (i in 0 until array.length()) {
            val Object = array.getJSONObject(i)
            Log.i("OcrdXOitm",Object.toString())
            val Entity = OcrdOitmEntity(
                0,
                Object.getString("id"),
                Object.getString("CardCode"),
                Object.getInt("LineNum"),
                Object.getString("ItemCode"),
                Object.getString("ShipToCode")
            )
            lista.add(Entity)

        }
        GlobalScope.launch(Dispatchers.IO) {
            ocrdOitmRepository.insertAllOitmXocrd(lista)
        }
    }

    fun insertarOitm(array: JSONArray) {
        val lista = mutableListOf<OitmEntity>()

        for (i in 0 until array.length()) {
            val Object = array.getJSONObject(i)
             val entity = OitmEntity(
                Object.getString("ItemCode"),
                Object.getString("ItemName"),
                Object.getString("CodeBars"),
                 Object.getString("U_TIPOHUEVO"),
                 Object.getString("Marca"),
                Object.getString("U_CanCaja")
            )
            lista.add(entity)

        }
        GlobalScope.launch(Dispatchers.IO) {
            oitmRepository.insertOitm(lista)
        }
    }
}
