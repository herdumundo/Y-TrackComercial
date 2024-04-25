package com.portalgm.y_trackcomercial.usecases.ventas.listaPrecios

import com.portalgm.y_trackcomercial.repository.ventasRepositories.A0_YTV_LISTA_PRECIOSRepository
import javax.inject.Inject

class ImportarListaPreciosUseCase @Inject constructor(
    private val A0_YTV_LISTA_PRECIOSRepository: A0_YTV_LISTA_PRECIOSRepository
)
{
    suspend fun Importar(): Int {
        return A0_YTV_LISTA_PRECIOSRepository.sincronizarApi()
    }
}