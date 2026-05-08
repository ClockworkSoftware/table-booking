package org.clockwork.tablebooking.controller

import org.clockwork.tablebooking.domain.Establishment
import org.clockwork.tablebooking.domain.Place
import org.clockwork.tablebooking.dto.place.PlaceCreationView
import org.clockwork.tablebooking.dto.place.PlaceView
import org.clockwork.tablebooking.dto.user.UserRole
import org.clockwork.tablebooking.exception.EntityNotFoundException
import org.clockwork.tablebooking.exception.UnprocessableEntityException
import org.clockwork.tablebooking.repository.EstablishmentRepository
import org.clockwork.tablebooking.repository.PlaceRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/public/place")
class PlaceController (
    val placeRepository: PlaceRepository,
    val establishmentRepository: EstablishmentRepository
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

    @GetMapping
    fun getPlaces(): ResponseEntity<List<PlaceView>> {
        return ResponseEntity.ok(placeRepository.findAll().map { it.toDto() })
    }

    @GetMapping("{id}")
    fun getPlace(@RequestParam id: Long): ResponseEntity<PlaceView> {
        return placeRepository.findById(id)
            .orElseThrow { EntityNotFoundException(Place::class.simpleName!!, id) }
            .let { ResponseEntity.ok(it.toDto()) }
    }

}