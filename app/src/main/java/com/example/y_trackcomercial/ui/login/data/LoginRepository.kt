package com.example.y_trackcomercial.ui.login.data

import com.cursokotlin.jetpackcomposeinstagram.login.data.network.LoginService
import javax.inject.Inject

class LoginRepository @Inject constructor(private val api: LoginService){

    suspend fun doLogin(user:String, password:String):Boolean{
        return api.doLoginServices(user, password)
    }
}
