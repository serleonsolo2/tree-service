package com.prewave.tree.exception

import org.jooq.exception.DataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionHandler {
    @ExceptionHandler(AlreadyExistsException::class)
    fun handleException(exception: AlreadyExistsException): ResponseEntity<*> =
        ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.message)

    @ExceptionHandler(InvalidTreeException::class)
    fun handleException(exception: InvalidTreeException): ResponseEntity<*> =
        ResponseEntity.status(HttpStatus.CONFLICT).body(exception.message)

    @ExceptionHandler(NotFoundException::class)
    fun handleException(exception: NotFoundException): ResponseEntity<*> =
        ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.message)

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleException(exception: IllegalArgumentException): ResponseEntity<*> =
        ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.message)

    @ExceptionHandler(DataAccessException::class)
    fun handleException(exception: DataAccessException): ResponseEntity<*> =
        ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.message)
}
