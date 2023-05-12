package com.example.y_trackcomercial.ui.login.domain

import com.example.y_trackcomercial.ui.login.data.LoginRepository
import javax.inject.Inject


class LoginUseCase @Inject constructor(private val repository: LoginRepository) {

    suspend operator fun invoke(user:String, password:String):Boolean{
        return repository.doLogin(user, password)
    }
}