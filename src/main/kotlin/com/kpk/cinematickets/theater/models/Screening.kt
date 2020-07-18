package com.kpk.cinematickets.theater.models

import java.math.BigDecimal
import java.time.LocalDateTime

data class Screening (
        val id: Long? = null,
        val time: LocalDateTime,
        val roomNumber: Long,
        val price: BigDecimal
)
