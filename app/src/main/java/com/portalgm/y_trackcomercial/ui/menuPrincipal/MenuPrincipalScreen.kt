package com.portalgm.y_trackcomercial.ui.menuPrincipal

import android.annotation.SuppressLint
import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Sync
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.portalgm.y_trackcomercial.BuildConfig
import com.portalgm.y_trackcomercial.R
import com.portalgm.y_trackcomercial.components.DialogLoading
import com.portalgm.y_trackcomercial.components.InfoDialog
import com.portalgm.y_trackcomercial.components.InfoDialogOk
import com.portalgm.y_trackcomercial.components.cardViewToolBar
import com.portalgm.y_trackcomercial.data.model.entities.RutasAccesosEntity
//import com.ytrack.y_trackcomercial.components.toIcon
import com.portalgm.y_trackcomercial.services.gps.locationLocal.LocationLocalViewModel
import com.portalgm.y_trackcomercial.ui.cambioPass.screen.InfoDialogNewPass
import com.portalgm.y_trackcomercial.ui.cambioPass.viewmodel.CambioPassViewModel
import com.portalgm.y_trackcomercial.ui.exportaciones.screen.ScreenExportaciones
import com.portalgm.y_trackcomercial.ui.exportaciones.viewmodel.ExportacionViewModel
import com.portalgm.y_trackcomercial.ui.facturacion.screen.FacturaScreen
import com.portalgm.y_trackcomercial.ui.facturacion.viewmodel.OinvViewModel
import com.portalgm.y_trackcomercial.ui.informeInventario.screen.ScreenInformeInventario
import com.portalgm.y_trackcomercial.ui.informeInventario.viewmodel.InformeInventarioViewModel
import com.portalgm.y_trackcomercial.ui.inventario.viewmodel.InventarioViewModel
import com.portalgm.y_trackcomercial.ui.inventario.screen.ScreenInventario
import com.portalgm.y_trackcomercial.ui.login2.LoginViewModel
import com.portalgm.y_trackcomercial.ui.marcacionPromotora.GpsLocationScreen
import com.portalgm.y_trackcomercial.ui.marcacionPromotora.MarcacionPromotoraViewModel
import com.portalgm.y_trackcomercial.ui.nuevaUbicacion.screen.NuevaUbicacionScreen
import com.portalgm.y_trackcomercial.ui.nuevaUbicacion.viewmodel.NuevaUbicacionViewModel
import com.portalgm.y_trackcomercial.ui.ordenVenta.screen.OrdenVentaScreen
import com.portalgm.y_trackcomercial.ui.ordenVenta.viewmodel.OrdenVentaViewModel
import com.portalgm.y_trackcomercial.ui.ordenVentaDetalle.screen.OrdenVentaDetalleScreen
import com.portalgm.y_trackcomercial.ui.ordenVentaDetalle.viewmodel.OrdenVentaDetalleViewModel
import com.portalgm.y_trackcomercial.ui.rastreoUsuarios.screen.RastreoUsuarioMapa
import com.portalgm.y_trackcomercial.ui.rastreoUsuarios.viewmodel.RastreoUsuariosViewModel
import com.portalgm.y_trackcomercial.ui.reimpresionFactura.screen.ReimpresionFacturaScreen
import com.portalgm.y_trackcomercial.ui.reimpresionFactura.viewmodel.ReimpresionFacturaViewModel
import com.portalgm.y_trackcomercial.ui.tablasRegistradas.ScreenTablasRegistradas
import com.portalgm.y_trackcomercial.ui.tablasRegistradas.TablasRegistradasViewModel
import com.portalgm.y_trackcomercial.ui.updateApp.UpdateAppScreen
import com.portalgm.y_trackcomercial.ui.updateApp.UpdateAppViewModel
import com.portalgm.y_trackcomercial.ui.visitaAuditor.viewmodel.VisitaAuditorViewModel
import com.portalgm.y_trackcomercial.ui.visitaAuditor.screen.VisitaAuditorScreen
import com.portalgm.y_trackcomercial.ui.visitaHorasTranscurridas.screen.VisitasHorasTranscurridasScreen
import com.portalgm.y_trackcomercial.ui.visitaHorasTranscurridas.viewmodel.VisitasHorasTranscurridasViewModel
import com.portalgm.y_trackcomercial.ui.visitaSupervisor.screen.VisitaSupervisorScreen
import com.portalgm.y_trackcomercial.ui.visitaSupervisor.viewmodel.VisitaSupervisorViewModel
import com.portalgm.y_trackcomercial.ui.visitasSinUbicacion.screen.VisitaSinUbicacionScreen
import com.portalgm.y_trackcomercial.ui.visitasSinUbicacion.viewmodel.VisitaSinUbicacionViewModel
import com.portalgm.y_trackcomercial.util.SharedPreferences
//import com.ytrack.y_trackcomercial.ui.registroEntradaPromotoras.cargar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

 @SuppressLint("UnusedMaterialScaffoldPaddingParameter", "SuspiciousIndentation")
@Composable
fun MenuPrincipal(
     loginViewModel: LoginViewModel,
     navControllerPrincipal: NavController,
     menuPrincipalViewModel: MenuPrincipalViewModel,
     tablasRegistradasViewModel: TablasRegistradasViewModel,
     locationViewModel: LocationLocalViewModel,
     marcacionPromotoraViewModel: MarcacionPromotoraViewModel,
     inventarioViewModel: InventarioViewModel,
     informeInventarioViewModel: InformeInventarioViewModel,
     visitaSupervisorViewModel: VisitaSupervisorViewModel,
     exportacionViewModel: ExportacionViewModel,
     visitaAuditorViewModel: VisitaAuditorViewModel,
     updateAppViewModel: UpdateAppViewModel,
     visitasHorasTranscurridasViewModel: VisitasHorasTranscurridasViewModel,
     rastreoUsuariosViewModel: RastreoUsuariosViewModel,
     nuevaUbicacionViewModel: NuevaUbicacionViewModel,
     cambioPassViewModel: CambioPassViewModel,
     sharedPreferences: SharedPreferences,
     visitaSinUbicacionViewModel: VisitaSinUbicacionViewModel,
     oinvViewModel: OinvViewModel,
     ordenVentaViewModel: OrdenVentaViewModel,
     ordenVentaDetalleViewModel: OrdenVentaDetalleViewModel,
     reimpresionFacturaViewModel: ReimpresionFacturaViewModel
 ) {
     //  if (loginViewModel.loggedIn.value == true) {
       if (sharedPreferences.getUserId()>0) {//SI YA SE INICIO SESION PARA QUE NO REQUIERA NUEVAMENTE AL CERRAR LA APP.
        val scaffoldState = rememberScaffoldState()
        val coroutineScope = rememberCoroutineScope()
        var expanded by rememberSaveable { mutableStateOf(false) }
        val navController = rememberNavController()
        val openDialogSesion = remember { mutableStateOf(false) }
        val openDialogSincro = remember { mutableStateOf(false) }
        val openDialogNewPass = remember { mutableStateOf(false) }
        val showDialog by menuPrincipalViewModel.showLoading.observeAsState(initial = false)
        val showDialogOK by menuPrincipalViewModel.showLoadingOk.observeAsState(initial = false)
        val mensajeDialog by menuPrincipalViewModel.mensajeDialog.observeAsState("")
         val activity = LocalContext.current as Activity
         val context = LocalContext.current
         val alertaNewPass by cambioPassViewModel.alerta.observeAsState(false)
         val mensajeNewPass by cambioPassViewModel.mensajeAlerta.observeAsState("")

        Scaffold(
              topBar = {
                MyTopAppBar(onNavIconClick = {
                    coroutineScope.launch {
                        scaffoldState.drawerState.open()
                    }
                },
                    expanded = expanded,
                    onMoreOptionsClick = { expanded = !expanded },
                    menuPrincipalViewModel,
                    openDialogSesion,
                    openDialogSincro,
                    openDialogNewPass,
                    activity,
                    marcacionPromotoraViewModel,navControllerPrincipal
                )
            },
            bottomBar={

                     if(alertaNewPass){
                         LaunchedEffect(alertaNewPass) {
                              val duration = Toast.LENGTH_SHORT
                             val toast = Toast.makeText(context, mensajeNewPass, duration)
                             toast.show()
                         }
                 }


            },
            scaffoldState = scaffoldState,
            /**drawerGesturesEnabled USAMOS PARA QUE NO SE PUEDA DESLIZAR EL MENU HACIA LA IZQUIERDA **/
            drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
            drawerContent = {
                MyDrawer(
                    //menu_items = navigationItems,
                    coroutineScope, scaffoldState, navController, menuPrincipalViewModel,tablasRegistradasViewModel
                )
            },

            ) {

            DialogLoading(mensajeDialog, showDialog)


            if (openDialogSesion.value) {

                // CustomDialog(openDialogCustom = openDialogSincro,"¿Deseas actualizar los datos del telefono?.",R.drawable.ic_sincro) { menuPrincipalViewModel.getOCRD() }
                InfoDialog(title = "Atención!",
                    desc = "¿Deseas cerrar sesión?.",
                    R.drawable.icono_exit,
                    {
                        //sharedPreferences.clearPreferences()
                        activity.finish()
                    },
                    onDismiss = {
                        openDialogSesion.value = false
                    })
            }

            if (openDialogSincro.value) {
                InfoDialog(title = "Atención!",
                    desc = "¿Deseas actualizar los datos del telefono?.",
                    R.drawable.icono_sincro,
                    { menuPrincipalViewModel.getOCRD() },
                    {
                        openDialogSincro.value = false
                    })
            }

            if (openDialogNewPass.value)
            {
                InfoDialogNewPass(
                R.drawable.icono_sincro,
                {
                    openDialogNewPass.value = false
                },
                    cambioPassViewModel)
            }
            if (showDialogOK) {
                InfoDialogOk(title = "Atención!",
                    desc = mensajeDialog,
                    R.drawable.icono_sincro,
                    { },
                    {
                        menuPrincipalViewModel.cerrarAviso()
                    })
            }

            NavHost(navController, startDestination = "home") {
                composable("home") {
                    HomeScreen()
                }
                composable("registroNuevo") {
                    ScreenTablasRegistradas(tablasRegistradasViewModel)
                }
                composable("inventario") {
                    ScreenInventario(inventarioViewModel)
                }

                composable("informeInventario") {
                    ScreenInformeInventario(informeInventarioViewModel)
                }

                composable("exportaciones") {
                    ScreenExportaciones(exportacionViewModel)
                }
                composable("rastreoUsuarios") {
                    RastreoUsuarioMapa(rastreoUsuariosViewModel)
                }

                composable("horasTranscurridas") {
                    VisitasHorasTranscurridasScreen(visitasHorasTranscurridasViewModel)
                }
                composable("asignacionUbicacion") {
                    NuevaUbicacionScreen(nuevaUbicacionViewModel)
                }

                composable("updateApp") {

                    UpdateAppScreen(updateAppViewModel)
                }
                composable("marcacionSupervisor") {
                    DisposableEffect(Unit) {
                        onDispose {
                            visitaSupervisorViewModel.limpiarValores()
                        }
                    }
                    VisitaSupervisorScreen(
                        locationViewModel = locationViewModel,
                        visitaSupervisorViewModel = visitaSupervisorViewModel
                    )
                }
                composable("marcacionAuditor") {
                    DisposableEffect(Unit) {
                        onDispose {
                            visitaAuditorViewModel.limpiarValores()
                        }
                    }
                    VisitaAuditorScreen(
                        locationViewModel = locationViewModel,
                        visitaAuditorViewModel = visitaAuditorViewModel
                    )
                }

                composable("marcacionPromotora") {
                    DisposableEffect(Unit) {
                        onDispose {
                            marcacionPromotoraViewModel.limpiarValores()
                        }
                    }
                    GpsLocationScreen(
                        locationViewModel = locationViewModel,
                        marcacionPromotoraViewModel = marcacionPromotoraViewModel
                    )
                }
                composable("marcacionPermisos") {
                    DisposableEffect(Unit) {
                        onDispose {
                            visitaSinUbicacionViewModel.limpiarValores()
                        }
                    }
                    VisitaSinUbicacionScreen(
                        visitaSinUbicacionViewModel = visitaSinUbicacionViewModel
                    )
                }
                //////////////////////////////////////////////FACTURACIONES
                composable("facturacion") {

                    FacturaScreen(oinvViewModel=oinvViewModel )
                }
                composable("ordenVenta") {

                    OrdenVentaScreen(
                        ordenVentaViewModel=ordenVentaViewModel,
                        navController )
                }
                composable(
                    "ordenVentaDetalle/{docNum}",
                    arguments = listOf(navArgument("docNum") { type = NavType.IntType })
                ) { backStackEntry ->
                    OrdenVentaDetalleScreen(
                        ordenVentaDetalleViewModel = ordenVentaDetalleViewModel,
                        docNum = backStackEntry.arguments?.getInt("docNum") ?: 0,
                        navController
                    )
                }
                composable("reimpresion") {

                    ReimpresionFacturaScreen(reimpresionFacturaViewModel=reimpresionFacturaViewModel )
                }


            }
        }
    } else {
        navControllerPrincipal.navigate("login")
    }
}

/*
private fun openWhatsApp(
    context: Context, menuPrincipalViewModel: MenuPrincipalViewModel
) {
    context.startActivity(
        // on below line we are opening the intent.
        Intent(
            // on below line we are calling
            // uri to parse the data
            Intent.ACTION_VIEW, Uri.parse(
                // on below line we are passing uri,
                // message and whats app phone number.
                java.lang.String.format(
                    "https://api.whatsapp.com/send?phone=%s&text=%s",
                    "595961873186",
                    "Buen dia, soy ${menuPrincipalViewModel.getUserName()}, necesito soporta para "
                )
            )
        )
    )
}*/


@SuppressLint("SuspiciousIndentation")
@Composable
fun MyTopAppBar(
    onNavIconClick: () -> Unit,
    expanded: Boolean,
    onMoreOptionsClick: () -> Unit,
    menuPrincipalViewModel: MenuPrincipalViewModel,
    openDialogCustom: MutableState<Boolean>,
    openDialogSincro: MutableState<Boolean>,
    openDialogNewPass: MutableState<Boolean>,
    activity: Activity,
    marcacionPromotoraViewModel: MarcacionPromotoraViewModel,
    navControllerPrincipal: NavController,
    ) {
    var showSnackbar by remember { mutableStateOf(false) }
    val registrosConPendiente: Int  by marcacionPromotoraViewModel.registrosConPendiente.observeAsState(0)
    val ocrdNameLivedata: String    by marcacionPromotoraViewModel.OcrdNameLivedata.observeAsState("")
    val socket by menuPrincipalViewModel.webSocketConectado.observeAsState(initial = false)

    val userName = menuPrincipalViewModel.getUserName()

      TopAppBar(backgroundColor = Color(if(socket){0xFFCE0303} else {0xFF000000}), contentColor = Color.White,

      //   TopAppBar(backgroundColor = Color(0xFFCE0303), contentColor = Color.White,

        navigationIcon = {
            IconButton(onClick = {
                onNavIconClick()
            }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Open Navigation Drawer",
                    tint = Color.White
                )
            }
        },

        title = {
            Column {
                Text(
                    text = userName +" -  ${BuildConfig.VERSION_NAME}" , color = Color.White, fontSize = 13.sp
                )
                Text(
                    text =  ocrdNameLivedata , color = Color.White, fontSize = 12.sp
                )
            }
        },
        actions = {
            IconButton(
                onClick = { onMoreOptionsClick() }, modifier = Modifier.padding(end = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = "More Options",
                    tint = Color.White
                )
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { onMoreOptionsClick() }) {

                DropdownMenuItem(onClick = {
                    openDialogSincro.value = true
                    onMoreOptionsClick()
                }) {

                    Icon(
                        imageVector = Icons.Default.Sync,
                        contentDescription = "Sincronizar datos",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Sincronizar datos")
                }

                DropdownMenuItem(onClick = {
                    openDialogCustom.value = true
                    onMoreOptionsClick()/*  activity.finish()*/
                }

                ) {
                    Icon(
                        imageVector = Icons.Default.ExitToApp,
                        contentDescription = "Cerrar sesión",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Cerrar sesión")
                }

                DropdownMenuItem(onClick = {
                    openDialogNewPass.value = true

                }

                ) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Cambiar contraseña",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Cambiar contraseña")
                }
            }
        })
    if (showSnackbar) {
        Snackbar(modifier = Modifier.padding(bottom = 16.dp), action = {
            Button(
                onClick = { showSnackbar = false },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFf910000)) // Color de fondo personalizado
            ) {
                Text(text = "Cerrar")
            }
        }) {
            Text(text = "Visita iniciada en: $ocrdNameLivedata")
        }
    }
}


@SuppressLint("SuspiciousIndentation")
@Composable
fun MyDrawer(
    coroutineScope: CoroutineScope,
    scaffoldState: ScaffoldState,
    navController: NavController,
    menuPrincipalViewModel: MenuPrincipalViewModel,
    tablasRegistradasViewModel: TablasRegistradasViewModel
) {
    val rol = menuPrincipalViewModel.getRol()
    val userName = menuPrincipalViewModel.getUserName()
    val acessoState by menuPrincipalViewModel.permisosUsuarios.observeAsState(initial = emptyList())


    val gradientColors: List<Color> = listOf(Color(0xFFf910000), Color(0xFF090808))
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(colors = gradientColors)),
        horizontalAlignment = Alignment.CenterHorizontally,
        //   contentPadding = PaddingValues(vertical = 36.dp)
    ) {
        // user's image
        Image(
            modifier = Modifier
                .size(size = 120.dp)
                .clip(shape = CircleShape),
            painter = painterResource(id = R.drawable.ytrack2),
            contentDescription = "Profile Image"
        )

        Text(
            modifier = Modifier.padding(top = 12.dp),
            text = userName,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        // user's email
        Text(
            modifier = Modifier.padding(top = 8.dp, bottom = 30.dp),
            text = rol,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            color = Color.White
        )
        Divider(
            Modifier
                .background(Color(0xFFEBE2E2))
                .height(1.dp)
                .fillMaxWidth()
        )
        DrawerItem2(acessoState,coroutineScope, scaffoldState, navController)

    }
}



@Composable
fun DrawerItem2(
    acessoState: List<RutasAccesosEntity>,
    coroutineScope: CoroutineScope,
    scaffoldState: ScaffoldState,
    navController: NavController
) {
    LazyColumn {
        acessoState.forEach { rutaAcceso ->
            val icon = rutaAcceso.icono  // Obtener el icono correspondiente
            val nombre = rutaAcceso.name
            val ruta = rutaAcceso.ruta
             item {
                 cardViewToolBar(
                     title = nombre,color = Color(0xFF000000),
                     icon = icon
                ) {
                    coroutineScope.launch {
                        scaffoldState.drawerState.close()
                    }
                    navController.navigate(ruta)
                }
             }
         }
    }
}








@Composable
fun HomeScreen() {

    Image(
        painter = painterResource(R.drawable.ytrack2), // Ruta de la imagen
        contentDescription = "My Image",
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight() // Tamaño de la imagen
    )
}
/*
@Composable
fun CustomerItem(customer: com.ytrack.y_trackcomercial.data.model.entities.OCRDEntity) {
    // Item de la lista para cada cliente
    Text(text = customer.Address)
    Text(text = customer.CardName!!)
    Divider()
}*/








