package com.kpk.cinematickets.tickets.models

import com.kpk.cinematickets.commons.BusinessException

open class InvalidTicketException(message: String): BusinessException(message)
class InvalidSeatsException(message: String): InvalidTicketException(message)
class InvalidScreeningException(message: String): InvalidTicketException(message)
