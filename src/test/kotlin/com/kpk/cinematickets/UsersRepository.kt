package com.kpk.cinematickets

import com.kpk.cinematickets.commons.SqlLoader
import org.slf4j.LoggerFactory
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import java.lang.invoke.MethodHandles

@Repository
class UsersRepository(val jdbcTemplate: NamedParameterJdbcTemplate) {

    private val logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().canonicalName)

    /**
     * To be used only for tests. Spring automatically inserts the users after the first oauth2 authentication.
     */
    fun insertDummyUser(name: String) {
        logger.info("Inserting dummy user with name: {}")

        val params = MapSqlParameterSource()
        params.addValue("name", name)

        jdbcTemplate.update(SqlLoader.INSERT_DUMMY_USER, params)
    }
    
}
