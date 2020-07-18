package com.kpk.cinematickets.tickets.models

open class InvalidTicketException(message: String): RuntimeException(message)
class InvalidSeatsException(message: String): InvalidTicketException(message)
class InvalidScreeningException(message: String): InvalidTicketException(message)
