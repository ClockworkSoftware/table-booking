package org.clockwork.tablebooking.controller

import org.clockwork.tablebooking.dto.reservation.ReservationSearchBody
import org.clockwork.tablebooking.dto.reservation.ReservationSearchScope
import org.clockwork.tablebooking.dto.reservation.ReservationView
import org.clockwork.tablebooking.dto.user.UserRole
import org.clockwork.tablebooking.exception.BadRequestException
import org.clockwork.tablebooking.repository.EstablishmentRepository
import org.clockwork.tablebooking.repository.ReservationRepository
import org.clockwork.tablebooking.repository.findByIdOrThrow
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/public/reservation")
class ReservationController(
    val reservationRepository: ReservationRepository,
    val establishmentRepository: EstablishmentRepository
) : BaseSecuredController() {

    @PostMapping("/{id}/begun")
    fun logReservationBegun(@RequestParam id: Long): ResponseEntity<Unit> {
        userContext.requireRole(UserRole.WAITER)

        val reservation = reservationRepository.findByIdOrThrow(id)
        reservation.begun = true
        reservationRepository.save(reservation)

        return ResponseEntity.ok().build()
    }

    @PostMapping("/search")
    fun searchReservations(
        @RequestParam scope: ReservationSearchScope,
        @RequestBody body: ReservationSearchBody
    ): ResponseEntity<List<ReservationView>> {
        return ResponseEntity.ok(
            when (scope) {
                ReservationSearchScope.SYSTEM -> {
                    userContext.requireRole(UserRole.ADMIN)
                    reservationRepository.findAll()
                }

                ReservationSearchScope.ESTABLISHMENT -> {
                    userContext.requireRole(UserRole.WAITER)

                    val establishmentId = body.establishmentId
                        ?: throw BadRequestException("Field \"establishmentId\" is empty")
                    val establishment = establishmentRepository.findByIdOrThrow(establishmentId)
                    reservationRepository.findByPlaceEstablishment(establishment)
                }

                ReservationSearchScope.USER -> {
                    userContext.requireRole(UserRole.CLIENT)
                    reservationRepository.findByClientId(userContext.currentUser.id)
                }
            }.map { it.toDto() }
        )
    }
}