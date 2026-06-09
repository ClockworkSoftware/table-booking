package org.clockwork.tablebooking.controller

import jakarta.transaction.Transactional
import org.clockwork.tablebooking.domain.Establishment
import org.clockwork.tablebooking.domain.Place
import org.clockwork.tablebooking.dto.place.PlaceCreationView
import org.clockwork.tablebooking.dto.place.PlaceUpdateView
import org.clockwork.tablebooking.dto.place.PlaceView
import org.clockwork.tablebooking.dto.reservation.ReservationCreationView
import org.clockwork.tablebooking.dto.reservation.ReservationView
import org.clockwork.tablebooking.dto.user.UserRole
import org.clockwork.tablebooking.exception.EntityNotFoundException
import org.clockwork.tablebooking.exception.UnprocessableEntityException
import org.clockwork.tablebooking.repository.EstablishmentRepository
import org.clockwork.tablebooking.repository.PlaceRepository
import org.clockwork.tablebooking.repository.findByIdOrThrow
import org.clockwork.tablebooking.service.PlaceService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/public/place")
class PlaceController(
    val placeRepository: PlaceRepository,
    val establishmentRepository: EstablishmentRepository,

    val placeService: PlaceService
) : BaseSecuredController() {

    @Transactional
    @PostMapping
    fun createPlace(@RequestBody body: PlaceCreationView): ResponseEntity<PlaceView> {
        userContext.requireRole(UserRole.ADMIN)

        if (placeRepository.findByLabelNumber(body.labelNumber).isPresent)
            throw UnprocessableEntityException("Place labelled ${body.labelNumber} already exists!")
        val establishment = establishmentRepository.findById(body.establishmentId)
            .orElseThrow {
                EntityNotFoundException(
                    Establishment::class.simpleName!!,
                    body.establishmentId
                )
            }

        val place = placeRepository.save(body.run {
            Place(
                labelNumber,
                reservationPrice,
                guestCapacity,
                establishment
            )
        })

        return ResponseEntity.status(201).body(place.toDto())
    }

    @PostMapping("/{id}/reserve")
    fun reserve(
        @PathVariable id: Long,
        @RequestBody body: ReservationCreationView
    ): ResponseEntity<ReservationView> {
        val reservation = placeService.makeReservation(
            id,
            body,
            userContext.currentUser.id
        )
        return ResponseEntity.ok(reservation.toDto())
    }

    @Transactional
    @GetMapping
    fun getPlaces(): ResponseEntity<List<PlaceView>> {
        return ResponseEntity.ok(placeRepository.findAll().map { it.toDto() })
    }

    @Transactional
    @GetMapping("{id}")
    fun getPlace(@PathVariable id: Long): ResponseEntity<PlaceView> {
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
    fun removePlace(@PathVariable id: Long): ResponseEntity<Unit> {
        userContext.requireRole(UserRole.ADMIN)

        placeRepository.deleteById(id)
        return ResponseEntity.ok().build()
    }

}