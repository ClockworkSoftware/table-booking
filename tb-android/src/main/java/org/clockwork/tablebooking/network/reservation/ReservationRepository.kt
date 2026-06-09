package org.clockwork.tablebooking.network.reservation

import org.clockwork.tablebooking.dto.reservation.ReservationSearchBody
import org.clockwork.tablebooking.dto.reservation.ReservationSearchScope
import org.clockwork.tablebooking.dto.reservation.ReservationView
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReservationRepository @Inject constructor(val apiService: ReservationApiService) {

    suspend fun getAll(): Result<List<ReservationView>> {
        return try {
            val response = apiService.search(
                ReservationSearchScope.SYSTEM,
                ReservationSearchBody()
            )
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getAllOwned(): Result<List<ReservationView>> {
        return try {
            val response = apiService.search(
                ReservationSearchScope.USER,
                ReservationSearchBody()
            )
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}