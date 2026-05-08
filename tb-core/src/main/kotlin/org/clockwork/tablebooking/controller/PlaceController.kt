package org.clockwork.tablebooking.controller

import org.clockwork.tablebooking.domain.Place
import org.clockwork.tablebooking.dto.place.CreatePlaceView
import org.clockwork.tablebooking.dto.place.PlaceView
import org.clockwork.tablebooking.dto.user.UserRole
import org.clockwork.tablebooking.exception.UnprocessableEntityException
import org.clockwork.tablebooking.repository.PlaceRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/public/place")
class PlaceController (
    val placeRepository: PlaceRepository
) : SecuredController() {

    @PostMapping
    fun createPlace(@RequestBody body: CreatePlaceView): ResponseEntity<Unit> {
        userContext.requireRole(UserRole.ADMIN)

        if (placeRepository.findByLabelNumber(body.labelNumber).isPresent)
            throw UnprocessableEntityException("Place labelled ${body.labelNumber} already exists!")
        placeRepository.save(Place.fromDto(body))

        return ResponseEntity.status(201).build()
    }

    @GetMapping
    fun getPlaces(): ResponseEntity<List<PlaceView>> {
        return ResponseEntity.ok(placeRepository.findAll().map { it.toDto() })
    }

//    @PostMapping
//    fun getPlacesForTime() {
//        if (placeRepository.findByLabelNumber(body.labelNumber).isPresent)
//            throw UnprocessableEntityException("Place labelled ${body.labelNumber} already exists!")
//        placeRepository.save(Place.fromDto(body))
//    }

}