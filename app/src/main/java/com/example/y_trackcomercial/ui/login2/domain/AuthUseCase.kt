package com.example.y_trackcomercial.ui.login2.domain

 import com.example.y_trackcomercial.ui.login2.data.AuthRepository
 import com.example.y_trackcomercial.ui.login2.data.network.response.Usuarios
 import javax.inject.Inject

class AuthUseCase @Inject constructor(private val repository: AuthRepository) {

    suspend operator fun invoke(user:String, password:String):List<Usuarios>?{
        return repository.iniciarSesionRepository(user, password)
    }
}