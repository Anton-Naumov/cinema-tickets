package com.kpk.cinematickets.movies

import com.kpk.cinematickets.commons.SqlLoader
import com.kpk.cinematickets.movies.models.Movie
import org.slf4j.LoggerFactory
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import java.lang.invoke.MethodHandles

@Repository
class MoviesRepository(val jdbcTemplate: NamedParameterJdbcTemplate) {

    private val logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().canonicalName)

    fun getMovies(): List<Movie> {
        logger.info("Getting all movies")

        return jdbcTemplate.query(SqlLoader.GET_ALL_MOVIES, MapSqlParameterSource()) { rs, _ ->
            Movie(
                    rs.getLong("id"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getLong("length_minutes"),
                    rs.getBigDecimal("rating"),
                    rs.getString("actors"),
                    listOf()
            )
        }
    }

}
