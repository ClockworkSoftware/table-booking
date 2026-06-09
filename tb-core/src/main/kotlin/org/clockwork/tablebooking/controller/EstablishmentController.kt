package org.clockwork.tablebooking.controller

import org.clockwork.tablebooking.domain.Establishment
import org.clockwork.tablebooking.dto.establishment.EstablishmentCreationShared
import org.clockwork.tablebooking.dto.establishment.EstablishmentView
import org.clockwork.tablebooking.dto.user.UserRole
import org.clockwork.tablebooking.exception.UnprocessableEntityException
import org.clockwork.tablebooking.repository.EstablishmentRepository
import org.clockwork.tablebooking.repository.UserRepository
import org.clockwork.tablebooking.repository.findByIdOrThrow
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/public/establishment")
class EstablishmentController (
    val establishmentRepository: EstablishmentRepository,
    val userRepository: UserRepository
) : BaseSecuredController() {

    @GetMapping
    fun getAll(): ResponseEntity<List<EstablishmentView>> {
        userContext.requireRole(UserRole.ADMIN)

        return ResponseEntity.ok(establishmentRepository.findAll().map(Establishment::toDto))
    }

    @GetMapping("{id}")
    fun getById(
        @PathVariable id: Long
    ): ResponseEntity<EstablishmentView> {
        return ResponseEntity.ok(establishmentRepository.findByIdOrThrow(id).toDto())
    }

    @PostMapping
    fun createEstablishment(
        @RequestBody body: EstablishmentCreationShared
    ): ResponseEntity<Unit> {
        userContext.requireRole(UserRole.ADMIN)
        establishmentRepository.findByAddress(body.address).ifPresent { throw UnprocessableEntityException(
            "Establishment entity with address ${body.address} already exists") }

        val user = userRepository.findByIdOrThrow(body.ownerId)
        val establishment = Establishment(body.address, user)
        establishmentRepository.save(establishment)

        return ResponseEntity.status(201).build()
    }

}