package com.kpk.cinematickets.theater.models

import java.math.BigDecimal

data class Movie (
        val id: Long,
        val title: String,
        val lengthMinutes: Long,
        val description: String,
        val rating: BigDecimal,
        val actors: String
) {
    override fun toString(): String {
        return "Title: $title, length: $lengthMinutes, resume: $description"
    }
}
