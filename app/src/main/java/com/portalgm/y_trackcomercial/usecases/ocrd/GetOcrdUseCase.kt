package com.portalgm.y_trackcomercial.usecases.ocrd

import com.portalgm.y_trackcomercial.data.model.models.OcrdItem
import com.portalgm.y_trackcomercial.repository.CustomerRepository
import javax.inject.Inject


class GetOcrdUseCase @Inject constructor(
    private val ocrdRepository: CustomerRepository
)
{
     fun Ocrd(): List<OcrdItem> {
        return ocrdRepository.getAddresses()
    }
}