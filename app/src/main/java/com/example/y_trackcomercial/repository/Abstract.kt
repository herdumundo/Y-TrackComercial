package com.example.y_trackcomercial.repository

 import kotlinx.coroutines.flow.Flow

interface Abstract {

    suspend fun savePhoneBook(phonebook: Token)

    suspend fun getPhoneBook():Flow<Token>
}