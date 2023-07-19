package com.example.y_trackcomercial.usecases.login

 import UsuarioResponse
 import com.example.y_trackcomercial.ui.login2.data.AuthRepository
 import javax.inject.Inject
 class AuthUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(user:String, password:String): UsuarioResponse?{
        return repository.iniciarSesionRepository(user, password)
    }
}