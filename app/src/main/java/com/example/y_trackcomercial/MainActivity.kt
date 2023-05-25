package com.example.y_trackcomercial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.y_trackcomercial.ui.login2.LoginScreen
import com.example.y_trackcomercial.ui.login2.LoginViewModel
import com.example.y_trackcomercial.ui.menuPrincipal.MenuPrincipal
import com.example.y_trackcomercial.ui.menuPrincipal.MenuPrincipalViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val loginViewModel: LoginViewModel by viewModels()
    private val menuPrincipalViewModel: MenuPrincipalViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val navController = rememberNavController()
                Router(navController, loginViewModel,menuPrincipalViewModel)
            }
        }
    }
}


@Composable
fun Router(
    navController: NavHostController,
    loginViewModel: LoginViewModel,
    menuPrincipalViewModel: MenuPrincipalViewModel
) {
    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") { LoginScreen(loginViewModel, navController) }
      composable("menu") { MenuPrincipal(loginViewModel, navController,menuPrincipalViewModel) }
        //  composable("menu") { sendWhatsAppMessage() }


    }
}