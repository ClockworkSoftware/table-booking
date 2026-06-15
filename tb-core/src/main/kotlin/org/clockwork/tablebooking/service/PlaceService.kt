package org.clockwork.tablebooking.service

import jakarta.transaction.Transactional
import org.clockwork.tablebooking.domain.Place
import org.clockwork.tablebooking.domain.Reservation
import org.clockwork.tablebooking.dto.place.PlaceView
import org.clockwork.tablebooking.dto.reservation.ReservationCreationView
import org.clockwork.tablebooking.exception.UnprocessableEntityException
import org.clockwork.tablebooking.repository.EstablishmentRepository
import org.clockwork.tablebooking.repository.PlaceRepository
import org.clockwork.tablebooking.repository.ReservationRepository
import org.clockwork.tablebooking.repository.UserRepository
import org.clockwork.tablebooking.repository.findByIdOrThrow
import org.springframework.stereotype.Service

@Service
class PlaceService(
    val placeRepository: PlaceRepository,
    val reservationRepository: ReservationRepository,
    val establishmentRepository: EstablishmentRepository,
    val userRepository: UserRepository
) {
    fun search(establishmentId: Long): List<PlaceView> {
        establishmentRepository.findByIdOrThrow(establishmentId)

        return placeRepository.findByEstablishmentId(establishmentId).map(Place::toDto)
    }

    @Transactional
    fun makeReservation(
        placeId: Long,
        body: ReservationCreationView,
        clientId: Long
    ): Reservation {
        val place = placeRepository.findByIdOrThrow(placeId)
        if (!place.isFreeBetween(body.start, body.finish))
            throw UnprocessableEntityException("At this time the specified place is already reserved!")
        val user = userRepository.findByIdOrThrow(body.clientId ?: clientId)

        return reservationRepository.save(
            Reservation(
                body.start,
                body.finish,
                false,
                user,
                place
            )
        )
    }
}