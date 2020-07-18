package com.kpk.cinematickets.tickets.models

import com.kpk.cinematickets.theater.models.Movie
import com.kpk.cinematickets.theater.models.Screening

data class ScreeningWithMovie(
        val movie: Movie,
        val screening: Screening
)
