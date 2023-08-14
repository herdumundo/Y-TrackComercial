package com.ytrack.y_trackcomercial.services.developerMode
import android.content.Context
import android.provider.Settings

fun isDeveloperModeEnabled(context: Context): Boolean {
    return Settings.Secure.getInt(
        context.contentResolver,
        Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, 0
    ) != 0
}
