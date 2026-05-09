package org.clockwork.tablebooking.controller

import org.clockwork.tablebooking.dto.user.UserRole
import org.clockwork.tablebooking.repository.ReservationRepository
import org.clockwork.tablebooking.repository.findByIdOrThrow
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/public/reservation")
class ReservationController (
    val reservationRepository: ReservationRepository
) : BaseSecuredController() {

    @PostMapping("/{id}")
    fun logReservationBegun(@RequestParam id: Long): ResponseEntity<Unit> {
        userContext.requireRole(UserRole.WAITER)

        val reservation = reservationRepository.findByIdOrThrow(id)
        reservation.begun = true
        reservationRepository.save(reservation)

        return ResponseEntity.ok().build()
    }

}