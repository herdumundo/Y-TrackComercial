package com.example.y_trackcomercial.services.developerMode

import android.content.Context
import android.database.ContentObserver
import android.net.Uri
import android.os.Handler
import android.provider.Settings
import javax.inject.Inject

class DeveloperModeObserver @Inject constructor(
    private val context: Context,
    handler: Handler
) : ContentObserver(handler) {

    private var listener: DeveloperModeListener? = null

    fun setDeveloperModeListener(listener: DeveloperModeListener) {
        this.listener = listener
    }

    override fun onChange(selfChange: Boolean, uri: Uri?) {
        super.onChange(selfChange, uri)
        if (uri.toString() == Settings.Global.getUriFor(Settings.Global.DEVELOPMENT_SETTINGS_ENABLED).toString()) {
            val developerModeEnabled = isDeveloperModeEnabled(context)
            listener?.onDeveloperModeChanged(developerModeEnabled)
        }
    }
}

interface DeveloperModeListener {
    fun onDeveloperModeChanged(developerModeEnabled: Boolean)
}
