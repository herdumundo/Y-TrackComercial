package com.example.y_trackcomercial.ui.login2.domain

 import UsuarioResponse
 import android.util.Log
 import com.example.y_trackcomercial.ui.login2.data.AuthRepository
 import com.example.y_trackcomercial.ui.login2.data.network.response.AuthTokenResponse
 import javax.inject.Inject
/*
class AuthUseCase @Inject constructor(private val repository: AuthRepository) {

    suspend operator fun invoke(user:String, password:String):List<Usuarios>?{
         return repository.iniciarSesionRepository(user, password)
    }
}*/


class AuthUseCase @Inject constructor(private val repository: AuthRepository) {

    suspend operator fun invoke(user:String, password:String): UsuarioResponse?{
        return repository.iniciarSesionRepository(user, password)
    }
}