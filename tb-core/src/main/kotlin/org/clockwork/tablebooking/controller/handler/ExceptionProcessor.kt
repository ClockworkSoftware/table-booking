package org.clockwork.tablebooking.controller.handler

import org.clockwork.tablebooking.exception.CommonHttpException
import org.clockwork.tablebooking.exception.InternalServerError
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionProcessor {

    @ExceptionHandler(value = [ CommonHttpException::class ])
    fun handle(exception: CommonHttpException): ResponseEntity<CommonHttpException> {
        return ResponseEntity
            .status(exception.code)
            .contentType(MediaType.APPLICATION_JSON)
            .body(exception)
    }

    @ExceptionHandler(value = [ Exception::class ])
    fun handle(exception: Exception): ResponseEntity<CommonHttpException> {
        return handle(InternalServerError("Unknown error, check the server logs"))
    }

}