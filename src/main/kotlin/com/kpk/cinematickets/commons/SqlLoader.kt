package com.kpk.cinematickets.commons

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.lang.invoke.MethodHandles

@Component
class SqlLoader(@Value("\${sql.container}") sqlFilesPath: String) {

    private val logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().canonicalName)

    companion object Queries {
        lateinit var GET_ALL_MOVIES: String
        lateinit var GET_MOVIE_SCREENINGS: String
        lateinit var GET_SCREENING_SEATS: String
        lateinit var INSERT_TICKET: String
        lateinit var GET_SCREENING: String
        lateinit var GET_CLIENT_MONEY_FOR_UPDATE: String
        lateinit var GET_CLIENT_MONEY: String
        lateinit var UPDATE_CLIENT_MONEY: String
        lateinit var INSERT_MOVIE: String
        lateinit var INSERT_ROOM: String
        lateinit var INSERT_SEAT: String
        lateinit var INSERT_SCREENING: String
        lateinit var INSERT_DUMMY_USER: String
        lateinit var GET_TICKET_INFO: String
    }

    init {
        SqlLoader::class.java.fields
                .filter { it.name != "Queries" }
                .forEach {
                    it.isAccessible = true
                    val fieldValue = getSQLStatement(sqlFilesPath, it.name.toLowerCase() + ".sql")
                    it.set(this, fieldValue)
                }
    }

    private fun getSQLStatement(sqlPath: String, sqlFileName: String): String {
        try {
            val filePath = "/$sqlPath/$sqlFileName"
            return this::class.java.getResource(filePath).readText(Charsets.UTF_8)
        } catch (e: Exception) {
            logger.info("Missing file: $sqlPath $sqlFileName")
            throw e
        }
    }
}
