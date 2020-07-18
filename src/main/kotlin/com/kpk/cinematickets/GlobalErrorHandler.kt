package com.kpk.cinematickets

import com.kpk.cinematickets.commons.BUSINESS_ERROR_CODE
import com.kpk.cinematickets.commons.BusinessException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import java.lang.invoke.MethodHandles

@ControllerAdvice
class GlobalErrorHandler {
    private val logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().canonicalName)

    @ExceptionHandler(BusinessException::class)
    fun handleException(ex: BusinessException, request: WebRequest): ResponseEntity<String> {
        logger.error("${request.contextPath} Business error: ${ex.message}", ex)
        return ResponseEntity.status(BUSINESS_ERROR_CODE).body(ex.message)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception, request: WebRequest): ResponseEntity<String> {
        logger.error("${request.contextPath} Error: ${ex.message}", ex)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error")
    }

}
