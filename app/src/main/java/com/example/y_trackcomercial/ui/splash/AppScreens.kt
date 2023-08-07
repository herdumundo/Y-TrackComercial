package com.example.y_trackcomercial.ui.splash

sealed class AppScreens  ( val route :String) {
    object SplashScreen: AppScreens("splash_screen")
    object MainScreen: AppScreens("splash_screen")
}
