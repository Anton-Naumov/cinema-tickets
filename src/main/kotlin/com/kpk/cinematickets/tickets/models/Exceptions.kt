package com.kpk.cinematickets.tickets.models

open class TicketPurchaseException(message: String): RuntimeException(message)
class InvalidSeatsException(message: String): TicketPurchaseException(message)
class InvalidScreeningException(message: String): TicketPurchaseException(message)
