package com.portalgm.y_trackcomercial.services.gps.locatioGoogleMaps

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast

fun openGoogleMapsWithMyLocation(context: Context) {
    val mapPackage = if (isPackageInstalled(context, "com.google.android.apps.maps")) {
        "com.google.android.apps.maps" // Google Maps está instalado
    } else if (isPackageInstalled(context, "com.google.android.apps.mapslite")) {
        "com.google.android.apps.mapslite" // Maps Go está instalado
    } else {
        null // Ninguna de las aplicaciones está instalada
    }

    if (mapPackage != null) {
        val gmmIntentUri = Uri.parse("geo:0,0")

        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage(mapPackage)

        if (mapIntent.resolveActivity(context.packageManager) != null) {
            context.startActivity(mapIntent)
        } else {
            // No se pudo resolver la actividad, es posible que ni Google Maps ni Maps Go estén instalados
            Toast.makeText(context, "No se encontró una aplicación de mapas instalada.", Toast.LENGTH_SHORT).show()
        }
    } else {
        // Ni Google Maps ni Maps Go están instalados
        Toast.makeText(context, "Ninguna aplicación de mapas de Google está instalada.", Toast.LENGTH_SHORT).show()
    }
}

private fun isPackageInstalled(context: Context, packageName: String): Boolean {
    return try {
        context.packageManager.getPackageInfo(packageName, 0)
        true
    } catch (e: PackageManager.NameNotFoundException) {
        false
    }
}

