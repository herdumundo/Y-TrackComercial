package com.example.y_trackcomercial.ui.informeInventario.screen

import android.app.DatePickerDialog
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.DatePicker
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.material.icons.filled.Search
import com.example.y_trackcomercial.ui.informeInventario.viewmodel.InformeInventarioViewModel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun ScreenInformeInventario(informeInventarioViewModel: InformeInventarioViewModel) {
    LaunchedEffect(Unit ){
        informeInventarioViewModel.limpiarGrilla()
    }
        MyContent(informeInventarioViewModel)
}

@Composable
fun MyContent(informeInventarioViewModel: InformeInventarioViewModel) {
    val mContext = LocalContext.current
    val mYear: Int
    val mMonth: Int
    val mDay: Int
    val mCalendar = Calendar.getInstance()
    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)
    mCalendar.time = Date()

    val mDate by informeInventarioViewModel.fecha.observeAsState(initial = "Seleccione Fecha")
    val grilla by informeInventarioViewModel.grilla.observeAsState("")

    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            val formattedMonth = String.format(Locale.US, "%02d", mMonth + 1)
            val formattedDayOfMonth = String.format(Locale.US, "%02d", mDayOfMonth)

            informeInventarioViewModel.setFecha("$formattedDayOfMonth/$formattedMonth/$mYear")
        }, mYear, mMonth, mDay
    )
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ButtonCalendar(
            mDate, Icons.Default.EditCalendar, Color(0XFF0F9D58)
        ) {   mDatePickerDialog.show() }
        Spacer(modifier = Modifier.size(10.dp))

        ButtonCalendar(
            "Buscar", Icons.Default.Search, Color(0xFFCE0303)
        ) { informeInventarioViewModel.getInformeInventario() }
        Spacer(modifier = Modifier.size(10.dp))

        MyWebView(grilla)
    }
}

@Composable
fun ButtonCalendar(
    textButton: String, imageVector: ImageVector, color: Color, accionBoton: () -> Unit
) {
    Button(
        modifier = Modifier.width(300.dp),
        onClick = accionBoton,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = color, contentColor = Color.White
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = textButton,
                modifier = Modifier.weight(1f) // Ajusta el ancho del Text para llenar el espacio disponible
            )
            Icon(
                imageVector = imageVector,//
                contentDescription = "Seleccionar punto de venta",
                modifier = Modifier.padding(start = 18.dp) // Ajusta el espaciado aquí
            )
        }
    }
}
@Composable
fun MyWebView(grilla: String) {
    val context = LocalContext.current
    val webView = remember { WebView(context) }
    val webViewScrollState = rememberScrollState()

    LaunchedEffect(grilla) {
        webView.apply {
            settings.javaScriptEnabled = true
           // settings.setSupportZoom(true)
            settings.builtInZoomControls = true
            settings.displayZoomControls = false
         //   settings.loadWithOverviewMode = true
          //  settings.useWideViewPort = true
            // Después de configurar otros ajustes en la WebView

            loadDataWithBaseURL(null, grilla.trimIndent(), "text/html", "utf-8", null)

            webChromeClient = WebChromeClient()

        }
    }
    AndroidView(
       modifier = Modifier
        .horizontalScroll(webViewScrollState),
        factory = { webView }
    )
}


