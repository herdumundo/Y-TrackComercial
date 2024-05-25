package com.portalgm.y_trackcomercial.ui.tablasRegistradas

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import com.portalgm.y_trackcomercial.R
import com.portalgm.y_trackcomercial.components.cardViewLoadingTablas

@Composable
fun ScreenTablasRegistradas(
    tablasRegistradasViewModel: TablasRegistradasViewModel,
) {
    val colorCard=Color(0xFFB90000)
    val tablaInfo = listOf(
        TablaInfo("Permisos de horarios", tablasRegistradasViewModel.permisoVisitaCount, R.drawable.ic_clock_permiso, tablasRegistradasViewModel.loadingpermisoVisitaCount, 9, "Obteniendo permisos..."),
        TablaInfo("Clientes cargados", tablasRegistradasViewModel.ocrdCount, R.drawable.ic_clientes, tablasRegistradasViewModel.loadingOcrdCount, 1, "Actualizando Clientes..."),
        TablaInfo("Ubicaciones cargadas", tablasRegistradasViewModel.ocrdUbicacionesCount, R.drawable.ic_map, tablasRegistradasViewModel.loadingUbicacionesCount, 0, "Actualizando ubicaciones..."),
        TablaInfo("Permisos otorgados", tablasRegistradasViewModel.rutasAccesoCount, R.drawable.ic_permisos, tablasRegistradasViewModel.loadingRutasAccesosCount, 3, "Actualizando permisos otorgados..."),
        TablaInfo("Lotes disponibles", tablasRegistradasViewModel.lotesListasCount, R.drawable.ic_lotes, tablasRegistradasViewModel.loadingLotesListasCount, 4, "Actualizando lotes disponibles..."),
        TablaInfo("Horarios cargados", tablasRegistradasViewModel.horariosUsuarioCount, R.drawable.ic_step, tablasRegistradasViewModel.loadingHorariosUsuarioCount, 5, "Actualizando horarios cargados..."),
        TablaInfo("Productos disponibles", tablasRegistradasViewModel.oitmCount, R.drawable.ic_products, tablasRegistradasViewModel.loadingOitmCount, 6, "Actualizando productos disponibles..."),
        TablaInfo("Productos por punto venta", tablasRegistradasViewModel.ocrdOitmCount, R.drawable.ic_products, tablasRegistradasViewModel.loadingOcrdOitmCount, 7, "Actualizando productos por punto venta..."),
        TablaInfo("Depositos en punto de venta", tablasRegistradasViewModel.ubicacionesPvCount, R.drawable.ic_lugar, tablasRegistradasViewModel.loadingUbicacionesPvCount, 8, "Actualizando depositos ..."),
        TablaInfo("Vendedores", tablasRegistradasViewModel.vendedorCount, R.drawable.ic_clientes, tablasRegistradasViewModel.loadingVendedorCount, 10, "Actualizando vendedores ..."),
        TablaInfo("Lista de precios", tablasRegistradasViewModel.listaPrecioCount, R.drawable.ic_egg, tablasRegistradasViewModel.loadingListaPrecioCount, 11, "Actualizando lista de precios ..."),
        TablaInfo("Stock por almacen", tablasRegistradasViewModel.almacenStockCount, R.drawable.ic_egg, tablasRegistradasViewModel.loadingAlmacenStockCount, 12, "Actualizando stock ..."),
        TablaInfo("Ordenes de ventas", tablasRegistradasViewModel.ordenVentaCount, R.drawable.ic_products, tablasRegistradasViewModel.loadingordenVenta, 13, "Actualizando ordenes de ventas ..."))
     LaunchedEffect(Unit) {
        tablasRegistradasViewModel.getTablasRegistradas()
    }
    LazyColumn {
        items(tablaInfo) { info ->
            cardViewLoadingTablas(
                textoLoading = info.textoLoading,
                color = colorCard,
                title = info.title,
                subTitle = info.count.observeAsState().value.toString(),
                image = info.image,
                isLoading = info.isLoading.observeAsState(false).value
            ) {
                tablasRegistradasViewModel.actualizarDatos(info.updateId)
            }
        }
    }
}
data class TablaInfo(
    val title: String,
    val count: LiveData<Int>,
    val image: Int,
    val isLoading: LiveData<Boolean>,
    val updateId: Int,
    val textoLoading: String = "Cargando..." // Ejemplo de un valor por defecto
)