package com.kpk.cinematickets.tickets

import com.kpk.cinematickets.commons.SqlLoader
import com.kpk.cinematickets.tickets.models.SeatTakenException
import org.slf4j.LoggerFactory
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import java.lang.invoke.MethodHandles

@Repository
class TicketsRepository(val jdbcTemplate: NamedParameterJdbcTemplate) {

    private val logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().canonicalName)

    @Throws(SeatTakenException::class)
    fun insertTicket(screeningId: Long, seatNumber: Long, uniqueTicketId: String, buyerEmail: String) {
        logger.info("Inserting ticket for screening : {}, seat number: {}, uniqueTicketId: {}, buyerEmail: {}",
                screeningId, seatNumber, uniqueTicketId, buyerEmail)

        val params = MapSqlParameterSource()
        params.addValue("screeningId", screeningId)
        params.addValue("seatNumber", seatNumber)
        params.addValue("uniqueTicketId", uniqueTicketId)
        params.addValue("buyerEmail", buyerEmail)

        try {
            jdbcTemplate.update(SqlLoader.INSERT_TICKET, params)
        } catch (ex: Exception) {// TODO check if seat_taken constraint
            throw SeatTakenException("Seat $seatNumber is already taken")
        }
    }

}
