package com.portalgm.y_trackcomercial.services.time_zone

import android.content.Context
import android.provider.Settings

fun isAutomaticTimeZone(context: Context): Int {
    val timeZone = Settings.Global.getInt(context.contentResolver, Settings.Global.AUTO_TIME_ZONE)
    return timeZone ?: 0
}


fun isAutomaticDateTime(context: Context): Int {
    val timeZone = Settings.Global.getInt(context.contentResolver, Settings.Global.AUTO_TIME)
    return timeZone ?: 0
}