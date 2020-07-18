package com.kpk.cinematickets.tickets.models

open class TicketPurchaseException(message: String): RuntimeException(message)
class SeatTakenException(message: String): TicketPurchaseException(message)
class InvalidScreeningException(message: String): TicketPurchaseException(message)
