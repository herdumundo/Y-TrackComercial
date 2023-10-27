package com.portalgm.y_trackcomercial.core.di

import com.portalgm.y_trackcomercial.repository.CustomerRepository
import com.portalgm.y_trackcomercial.repository.LotesListasRepository
import com.portalgm.y_trackcomercial.repository.OcrdOitmRepository
import com.portalgm.y_trackcomercial.repository.OcrdUbicacionesRepository
import com.portalgm.y_trackcomercial.repository.OitmRepository
import com.portalgm.y_trackcomercial.services.websocket.PieSocketListener
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // Ajusta el alcance según tus necesidades
object WebSocketModule {
    @Provides
    @Singleton
    fun provideWebSocketListener(
        customerRepository: CustomerRepository,
        lotesListasRepository: LotesListasRepository,
        ocrdUbicacionesRepository: OcrdUbicacionesRepository,
        ocrdOitmRepository : OcrdOitmRepository,
        oitmRepository: OitmRepository
    ): PieSocketListener {
        return PieSocketListener(
            customerRepository,
            lotesListasRepository,
            ocrdUbicacionesRepository,
            ocrdOitmRepository,
            oitmRepository

        )
    }
}
