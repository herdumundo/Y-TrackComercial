package com.portalgm.y_trackcomercial.services.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.widget.Toast

@SuppressLint("MissingPermission")
class BluetoothPairingHelper(private val context: Context) {
    private val bluetoothAdapter: BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

    private val receiver = object : BroadcastReceiver() {
        @SuppressLint("MissingPermission")
        override fun onReceive(context: Context?, intent: Intent?) {
            val action = intent?.action
            when (action) {
                BluetoothDevice.ACTION_PAIRING_REQUEST -> {
                    val device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                    val pin = intent.getIntExtra(BluetoothDevice.EXTRA_PAIRING_KEY, BluetoothDevice.ERROR)
                    if (pin == 0) { // Cambia esto al PIN que necesites
                        // Acepta el emparejamiento
                        device?.setPin("0000".toByteArray()) // Cambia esto al PIN que necesites
                        device?.setPairingConfirmation(true)
                    } else {
                        // Rechaza el emparejamiento
                        device?.setPairingConfirmation(false)
                    }
                }
                BluetoothDevice.ACTION_BOND_STATE_CHANGED -> {
                    val state = intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, BluetoothDevice.ERROR)
                    val prevState = intent.getIntExtra(BluetoothDevice.EXTRA_PREVIOUS_BOND_STATE, BluetoothDevice.ERROR)
                    when (state) {
                        BluetoothDevice.BOND_BONDED -> {
                            // El emparejamiento se ha completado con éxito
                            Toast.makeText(context, "Emparejado con éxito", Toast.LENGTH_SHORT).show()
                        }
                        BluetoothDevice.BOND_BONDING -> {
                            // El emparejamiento está en progreso
                        }
                        BluetoothDevice.BOND_NONE -> {
                            // El emparejamiento ha fallado o se ha eliminado
                            Toast.makeText(context, "Fallo en el emparejamiento", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    init {
        if (bluetoothAdapter == null) {
            // El dispositivo no admite Bluetooth
            Toast.makeText(context, "El dispositivo no admite Bluetooth", Toast.LENGTH_SHORT).show()
        } else {
            // Habilitar Bluetooth si no está habilitado
            if (!bluetoothAdapter.isEnabled) {
                val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                context.startActivity(enableBtIntent)
            }

            // Registra el receptor para eventos de emparejamiento
            val filter = IntentFilter()
            filter.addAction(BluetoothDevice.ACTION_PAIRING_REQUEST)
            filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED)
            context.registerReceiver(receiver, filter)
        }
    }

    fun initiatePairing(deviceAddress: String) {
        // Emparejar con la impresora Bluetooth (reemplaza con la dirección MAC de tu impresora)
        val device = bluetoothAdapter.getRemoteDevice(deviceAddress)
        device.createBond()
    }

    fun close() {
        // Desregistra el receptor y realiza otras tareas de limpieza si es necesario
        context.unregisterReceiver(receiver)
    }
}
