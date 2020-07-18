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
    fun insertTicket(screeningId: Long, seatId: Long, uniqueTicketId: String, buyerName: String) {
        logger.info("Inserting ticket for screening : {}, seat id: {}, uniqueTicketId: {}, buyerName: {}",
                screeningId, seatId, uniqueTicketId, buyerName)

        val params = MapSqlParameterSource()
        params.addValue("screeningId", screeningId)
        params.addValue("seatId", seatId)
        params.addValue("uniqueTicketId", uniqueTicketId)
        params.addValue("buyerName", buyerName)

        try {
            jdbcTemplate.update(SqlLoader.INSERT_TICKET, params)
        } catch (ex: Exception) {// TODO check if seat_taken constraint
            throw SeatTakenException("Seat $seatId is already taken")
        }
    }

}
