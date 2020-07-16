package com.kpk.cinematickets.movies.models

import java.math.BigDecimal

data class Movie (
        val id: Long,
        val title: String,
        val description: String,
        val length_minutes: Long,
        val rating: BigDecimal,
        val actors: String,
        val screenings: List<Screening>
)
