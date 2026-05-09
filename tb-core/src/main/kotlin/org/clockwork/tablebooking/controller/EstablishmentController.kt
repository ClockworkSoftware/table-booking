package org.clockwork.tablebooking.controller

import org.clockwork.tablebooking.domain.Establishment
import org.clockwork.tablebooking.dto.establishment.EstablishmentCreationView
import org.clockwork.tablebooking.dto.user.UserRole
import org.clockwork.tablebooking.exception.UnprocessableEntityException
import org.clockwork.tablebooking.repository.EstablishmentRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/public/establishment")
class EstablishmentController (
    val establishmentRepository: EstablishmentRepository
) : BaseSecuredController() {

    @PostMapping
    fun createEstablishment(@RequestBody body: EstablishmentCreationView): ResponseEntity<Unit> {
        userContext.requireRole(UserRole.ADMIN)

        establishmentRepository.findByAddress(body.address).ifPresent { throw UnprocessableEntityException(
            "Establishment entity with address ${body.address} already exists") }
        establishmentRepository.save(Establishment(body.address))

        return ResponseEntity.status(201).build()
    }

}