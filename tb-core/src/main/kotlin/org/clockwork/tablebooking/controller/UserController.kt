package org.clockwork.tablebooking.controller

import org.clockwork.tablebooking.domain.Reservation
import org.clockwork.tablebooking.dto.place.PlaceUpdateView
import org.clockwork.tablebooking.dto.place.PlaceView
import org.clockwork.tablebooking.dto.reservation.ReservationCreationView
import org.clockwork.tablebooking.dto.user.UserRole
import org.clockwork.tablebooking.dto.user.UserView
import org.clockwork.tablebooking.exception.UnprocessableEntityException
import org.clockwork.tablebooking.repository.UserRepository
import org.clockwork.tablebooking.repository.findByIdOrThrow
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/public")
class UserController (
    val userRepository: UserRepository
) : BaseSecuredController() {

    @GetMapping("whoami")
    fun whoami(): ResponseEntity<UserView> {
        val user = userRepository.findByIdOrThrow(userContext.jwtView.sub.toLong())
        return ResponseEntity.ok(user.toDto())
    }

}