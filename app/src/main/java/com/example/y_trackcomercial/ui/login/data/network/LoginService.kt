package com.cursokotlin.jetpackcomposeinstagram.login.data.network

 import com.example.y_trackcomercial.ui.login.data.network.LoginClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
 import javax.inject.Inject

class LoginService @Inject constructor(private val loginClient: LoginClient){
    suspend fun doLoginServices(user:String, password:String):Boolean{
        return withContext(Dispatchers.IO){
            val response = loginClient.doLogin()
            response.body()?.success ?: false
        }
    }
}