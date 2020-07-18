package com.kpk.cinematickets.theater

import com.kpk.cinematickets.commons.SqlLoader
import com.kpk.cinematickets.theater.models.Movie
import com.kpk.cinematickets.theater.models.Screening
import com.kpk.cinematickets.theater.models.Seat
import com.kpk.cinematickets.tickets.models.ScreeningWithMovie
import org.slf4j.LoggerFactory
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.jdbc.support.KeyHolder
import org.springframework.stereotype.Repository
import java.lang.invoke.MethodHandles
import java.math.BigDecimal
import java.sql.ResultSet
import java.time.LocalDateTime


@Repository
class TheaterRepository(val jdbcTemplate: NamedParameterJdbcTemplate) {

    private val logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().canonicalName)

    companion object {
        val movieRowMapper = { rs: ResultSet, _: Int ->
            Movie(
                    rs.getLong("movie_id"),
                    rs.getString("title"),
                    rs.getLong("length_minutes"),
                    rs.getString("description"),
                    rs.getBigDecimal("rating"),
                    rs.getString("actors")
            )
        }

        val movieScreeningRowMapper = { rs: ResultSet, _: Int ->
            Screening(
                    rs.getLong("screening_id"),
                    rs.getTimestamp("screening_time").toLocalDateTime(),
                    rs.getLong("room_number"),
                    rs.getBigDecimal("price")
            )
        }
    }

    fun getMovies(): List<Movie> {
        logger.info("Getting all movies")
        return jdbcTemplate.query(SqlLoader.GET_ALL_MOVIES, MapSqlParameterSource(), movieRowMapper)
    }

    fun getMovieScreenings(movieId: Long): List<Screening> {
        logger.info("Getting screenings for movie: {}", movieId)

        val params = MapSqlParameterSource()
        params.addValue("movieId", movieId)

        return jdbcTemplate.query(SqlLoader.GET_MOVIE_SCREENINGS, params, movieScreeningRowMapper)
    }

    fun getScreeningSeats(screeningId: Long): List<Seat> {
        logger.info("Getting seats for screening: {}", screeningId)

        val params = MapSqlParameterSource()
        params.addValue("screeningId", screeningId)

        return jdbcTemplate.query(SqlLoader.GET_SCREENING_SEATS, params) { rs, _ ->
            Seat(rs.getLong("id"), rs.getLong("number"), rs.getBoolean("is_free"))
        }
    }

    fun getScreeningWithMovie(screeningId: Long): ScreeningWithMovie? {
        logger.info("Getting screening with id: {}", screeningId)

        val params = MapSqlParameterSource()
        params.addValue("screeningId", screeningId)

        return jdbcTemplate.query(SqlLoader.GET_SCREENING, params) { rs, rowNum ->
            ScreeningWithMovie(
                    movieRowMapper(rs, rowNum),
                    movieScreeningRowMapper(rs, rowNum)
            )
        }.firstOrNull()
    }

    fun insertMovie(movie: Movie): Long {
        logger.info("Inserting movie: {}", movie)

        val params = MapSqlParameterSource()
        params.addValue("title", movie.title)
        params.addValue("lengthMinutes", movie.lengthMinutes)
        params.addValue("description", movie.description)
        params.addValue("rating", movie.rating)
        params.addValue("actors", movie.actors)

        val holder: KeyHolder = GeneratedKeyHolder()
        jdbcTemplate.update(SqlLoader.INSERT_MOVIE, params, holder, arrayOf("id"))
        return holder.key!!.toLong()
    }

    fun insertRoom(roomNumber: Long): Long {
        logger.info("Inserting room with number: {}", roomNumber)

        val params = MapSqlParameterSource()
        params.addValue("roomNumber", roomNumber)

        val holder: KeyHolder = GeneratedKeyHolder()
        jdbcTemplate.update(SqlLoader.INSERT_ROOM, params, holder, arrayOf("id"))
        return holder.key!!.toLong()
    }

    fun insertSeats(roomId: Long, seatNumber: Long): Long {
        logger.info("Inserting seat from room id: {} with number: {}", roomId, seatNumber)

        val params = MapSqlParameterSource()
        params.addValue("roomId", roomId)
        params.addValue("seatNumber", seatNumber)

        val holder: KeyHolder = GeneratedKeyHolder()
        jdbcTemplate.update(SqlLoader.INSERT_SEAT, params, holder, arrayOf("id"))
        return holder.key!!.toLong()
    }

    fun insertScreening(movieId: Long, roomId: Long, time: LocalDateTime, price: BigDecimal): Long {
        logger.info("Inserting movieId: {}, roomId: {}, time: {}, price: {}", movieId, roomId, time, price)

        val params = MapSqlParameterSource()
        params.addValue("movieId", movieId)
        params.addValue("roomId", roomId)
        params.addValue("time", time)
        params.addValue("price", price)

        val holder: KeyHolder = GeneratedKeyHolder()
        jdbcTemplate.update(SqlLoader.INSERT_SCREENING, params, holder, arrayOf("id"))
        return holder.key!!.toLong()
    }

}
