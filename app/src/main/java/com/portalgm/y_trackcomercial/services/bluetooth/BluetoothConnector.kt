package com.portalgm.y_trackcomercial.services.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import java.io.IOException
import java.util.*

class BluetoothConnector(private val deviceAddress: String) {
    private var bluetoothSocket: BluetoothSocket? = null

    @SuppressLint("MissingPermission")
    @Throws(IOException::class)
    fun connect(): BluetoothSocket? {
        val device: BluetoothDevice = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(deviceAddress)
        val uuid: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB") // Standard SerialPortService ID
        bluetoothSocket = device.createRfcommSocketToServiceRecord(uuid)
        bluetoothSocket?.connect()
        return bluetoothSocket
    }

    @Throws(IOException::class)
    fun disconnect() {
        bluetoothSocket?.close()
    }
}
