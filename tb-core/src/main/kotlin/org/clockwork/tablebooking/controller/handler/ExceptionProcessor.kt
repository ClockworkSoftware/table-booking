package org.clockwork.tablebooking.controller.handler

import org.apache.logging.log4j.kotlin.Logging
import org.clockwork.tablebooking.exception.BadRequestException
import org.clockwork.tablebooking.exception.CommonHttpException
import org.clockwork.tablebooking.exception.InternalServerError
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionProcessor : Logging {

    @ExceptionHandler(value = [ CommonHttpException::class ])
    fun handle(exception: CommonHttpException): ResponseEntity<CommonHttpException.SimplifiedView> {
        return ResponseEntity
            .status(exception.code)
            .contentType(MediaType.APPLICATION_JSON)
            .body(exception.simplifiedView())
    }

    @ExceptionHandler(value = [ MethodArgumentNotValidException::class, HttpMessageNotReadableException::class ])
    fun handleBadRequestException(exception: Exception): ResponseEntity<CommonHttpException.SimplifiedView> {
        logger.error(exception)
        return handle(BadRequestException("Bad request format. ${exception.message}"))
    }

    @ExceptionHandler(value = [ Exception::class ])
    fun handleUnknownException(exception: Exception): ResponseEntity<CommonHttpException.SimplifiedView> {
        logger.error(exception)
        return handle(InternalServerError("Unknown error, check the server logs"))
    }

}