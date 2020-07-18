package com.kpk.cinematickets.movies

import com.kpk.cinematickets.commons.SqlLoader
import com.kpk.cinematickets.movies.models.Movie
import com.kpk.cinematickets.movies.models.Screening
import com.kpk.cinematickets.movies.models.Seat
import com.kpk.cinematickets.tickets.models.InvalidScreeningException
import com.kpk.cinematickets.tickets.models.ScreeningWithMovie
import org.slf4j.LoggerFactory
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import java.lang.invoke.MethodHandles
import java.sql.ResultSet

@Repository
class MoviesRepository(val jdbcTemplate: NamedParameterJdbcTemplate) {

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
                    rs.getLong("room_number")
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
            Seat(rs.getLong("id"), rs.getLong("number"), rs.getBoolean("free"))
        }
    }

    fun getScreeningWithMovie(screeningId: Long): ScreeningWithMovie {
        logger.info("Getting screening with id: {}", screeningId)

        val params = MapSqlParameterSource()
        params.addValue("screeningId", screeningId)

        return jdbcTemplate.query(SqlLoader.GET_SCREENING, params) { rs, rowNum ->
            ScreeningWithMovie(
                    movieRowMapper(rs, rowNum),
                    movieScreeningRowMapper(rs, rowNum)
            )
        }.firstOrNull() ?: throw InvalidScreeningException("Screening with id $screeningId doesn't exist!")
    }
}
