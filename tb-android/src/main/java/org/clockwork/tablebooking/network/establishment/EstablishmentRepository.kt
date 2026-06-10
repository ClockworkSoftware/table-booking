package org.clockwork.tablebooking.network.establishment

import org.clockwork.tablebooking.dto.establishment.EstablishmentView
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EstablishmentRepository @Inject constructor(val api: EstablishmentApiService) {

    suspend fun getAll(): List<EstablishmentView> {
        TODO()
    }

}