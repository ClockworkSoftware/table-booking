package org.clockwork.tablebooking.network.reservation

import org.clockwork.tablebooking.dto.reservation.ReservationSearchBody
import org.clockwork.tablebooking.dto.reservation.ReservationSearchScope
import org.clockwork.tablebooking.dto.reservation.ReservationView
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface ReservationApiService {
    @POST("/api/v1/public/reservation/search")
    suspend fun search(
        @Query("scope") scope: ReservationSearchScope,
        @Body body: ReservationSearchBody
        ): List<ReservationView>
}