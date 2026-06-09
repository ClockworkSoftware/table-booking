package org.clockwork.tablebooking.controller

import org.clockwork.tablebooking.dto.user.UserView
import org.clockwork.tablebooking.repository.UserRepository
import org.clockwork.tablebooking.repository.findByIdOrThrow
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/public")
class UserController (
    val userRepository: UserRepository
) : BaseSecuredController() {

    @GetMapping("whoami")
    fun whoami(): ResponseEntity<UserView> {
        val user = userRepository.findByIdOrThrow(userContext.currentUser.id)
        return ResponseEntity.ok(user.toDto())
    }

}