package com.portalgm.y_trackcomercial.services.numberPhone

import android.annotation.SuppressLint
import android.content.Context
import android.telephony.TelephonyManager

@SuppressLint("HardwareIds", "MissingPermission")
fun getPhoneNumber(context: Context): String {
    val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    return telephonyManager.simSerialNumber ?: ""
}
