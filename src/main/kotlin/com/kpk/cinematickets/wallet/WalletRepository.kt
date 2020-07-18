package com.kpk.cinematickets.wallet

import com.kpk.cinematickets.commons.SqlLoader
import org.slf4j.LoggerFactory
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import java.lang.invoke.MethodHandles
import java.math.BigDecimal

@Repository
class WalletRepository(val jdbcTemplate: NamedParameterJdbcTemplate) {

    private val logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().canonicalName)

    fun getClientMoneyForUpdate(clientName: String): BigDecimal {
        logger.info("Getting client: {} money amount for update", clientName)

        val params = MapSqlParameterSource()
        params.addValue("clientName", clientName)

        return jdbcTemplate.query(SqlLoader.GET_CLIENT_MONEY_FOR_UPDATE, params) { rs, _ ->
            rs.getBigDecimal("money_amount")
        }.first()
    }

    fun updateClientMoneyAmount(clientName: String, money: BigDecimal) {
        logger.info("Updating client: {} money amount to: {}", clientName, money)

        val params = MapSqlParameterSource()
        params.addValue("clientName", clientName)
        params.addValue("money", money)

        jdbcTemplate.update(SqlLoader.UPDATE_CLIENT_MONEY, params)
    }

    fun getClientMoney(clientName: String): BigDecimal {
        logger.info("Getting client: {} money amount", clientName)

        val params = MapSqlParameterSource()
        params.addValue("clientName", clientName)

        return jdbcTemplate.query(SqlLoader.GET_CLIENT_MONEY, params) { rs, _ ->
            rs.getBigDecimal("money_amount")
        }.first()
    }

}
