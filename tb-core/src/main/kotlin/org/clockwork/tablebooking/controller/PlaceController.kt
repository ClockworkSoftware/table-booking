package org.clockwork.tablebooking.controller

import org.clockwork.tablebooking.domain.Establishment
import org.clockwork.tablebooking.domain.Place
import org.clockwork.tablebooking.domain.Reservation
import org.clockwork.tablebooking.dto.place.PlaceCreationView
import org.clockwork.tablebooking.dto.place.PlaceUpdateView
import org.clockwork.tablebooking.dto.place.PlaceView
import org.clockwork.tablebooking.dto.reservation.ReservationCreationView
import org.clockwork.tablebooking.dto.user.UserRole
import org.clockwork.tablebooking.exception.EntityNotFoundException
import org.clockwork.tablebooking.exception.UnprocessableEntityException
import org.clockwork.tablebooking.repository.EstablishmentRepository
import org.clockwork.tablebooking.repository.PlaceRepository
import org.clockwork.tablebooking.repository.UserRepository
import org.clockwork.tablebooking.repository.findByIdOrThrow
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/public/place")
class PlaceController (
    val placeRepository: PlaceRepository,
    val establishmentRepository: EstablishmentRepository,
    val userRepository: UserRepository
) : BaseSecuredController() {

    @PostMapping
    fun createPlace(@RequestBody body: PlaceCreationView): ResponseEntity<Unit> {
        userContext.requireRole(UserRole.ADMIN)

        if (placeRepository.findByLabelNumber(body.labelNumber).isPresent)
            throw UnprocessableEntityException("Place labelled ${body.labelNumber} already exists!")
        val establishment = establishmentRepository.findById(body.establishmentId)
            .orElseThrow { EntityNotFoundException(Establishment::class.simpleName!!, body.establishmentId) }

        placeRepository.save(body.run {
            Place(
                labelNumber,
                reservationPrice,
                guestCapacity,
                establishment
            )
        })

        return ResponseEntity.status(201).build()
    }

    @PostMapping("/{id}/reserve")
    fun createReservation(
        @RequestParam id: Long,
        @RequestBody body: ReservationCreationView
    ) : ResponseEntity<Reservation> {
        val place = placeRepository.findByIdOrThrow(id)
        if (place.isFreeBetween(body.start, body.finish))
            throw UnprocessableEntityException("At this time the specified place is already reserved!")
        val user = userRepository.findByIdOrThrow(body.clientId ?: userContext.jwtView.sub.toLong())

        val reservation = Reservation(
            body.start,
            body.finish,
            false,
            user,
            place
        )

        return ResponseEntity.ok(reservation)
    }

    @GetMapping
    fun getPlaces(): ResponseEntity<List<PlaceView>> {
        return ResponseEntity.ok(placeRepository.findAll().map { it.toDto() })
    }

    @GetMapping("{id}")
    fun getPlace(@RequestParam id: Long): ResponseEntity<PlaceView> {
        return placeRepository.findByIdOrThrow(id).let { ResponseEntity.ok(it.toDto()) }
    }

    @PutMapping
    fun updatePlace(@RequestBody body: PlaceUpdateView): ResponseEntity<PlaceView> {
        userContext.requireRole(UserRole.ADMIN)

        val place = placeRepository.findByIdOrThrow(body.id)
        place.run {
            labelNumber = body.labelNumber ?: labelNumber
            reservationPrice = body.reservationPrice ?: reservationPrice
            guestCapacity = body.guestCapacity ?: guestCapacity
        }
        placeRepository.save(place)

        return ResponseEntity.ok(place.toDto())
    }

    @DeleteMapping("{id}")
    fun removePlace(@RequestParam id: Long): ResponseEntity<Unit> {
        userContext.requireRole(UserRole.ADMIN)

        placeRepository.deleteById(id)
        return ResponseEntity.ok().build()
    }

}