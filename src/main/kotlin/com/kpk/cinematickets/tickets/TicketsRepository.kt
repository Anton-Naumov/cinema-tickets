package com.kpk.cinematickets.tickets

import com.kpk.cinematickets.commons.SqlLoader
import com.kpk.cinematickets.tickets.models.Ticket
import org.slf4j.LoggerFactory
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import java.lang.invoke.MethodHandles

@Repository
class TicketsRepository(val jdbcTemplate: NamedParameterJdbcTemplate) {

    private val logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().canonicalName)

    fun insertTicket(screeningId: Long, seatId: Long, uniqueTicketId: String, buyerName: String) {
        logger.info("Inserting ticket for screening : {}, seat id: {}, uniqueTicketId: {}, buyerName: {}",
                screeningId, seatId, uniqueTicketId, buyerName)

        val params = MapSqlParameterSource()
        params.addValue("screeningId", screeningId)
        params.addValue("seatId", seatId)
        params.addValue("uniqueTicketId", uniqueTicketId)
        params.addValue("buyerName", buyerName)

        jdbcTemplate.update(SqlLoader.INSERT_TICKET, params)
    }

    fun getTickets(clientName: String): List<Ticket> {
        logger.info("Getting tickets for client name: {}", clientName)

        val params = MapSqlParameterSource()
        params.addValue("clientName", clientName)

        return jdbcTemplate.query(SqlLoader.GET_TICKET_INFO, params) { rs, _ ->
            Ticket(
                    rs.getString("unique_id"),
                    rs.getString("movie_title"),
                    rs.getTimestamp("screening_time").toLocalDateTime(),
                    rs.getLong("screening_room"),
                    rs.getLong("seat_number")
            )
        }
    }

}
