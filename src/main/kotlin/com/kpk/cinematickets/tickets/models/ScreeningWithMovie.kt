package com.kpk.cinematickets.tickets.models

import com.kpk.cinematickets.movies.models.Movie
import com.kpk.cinematickets.movies.models.Screening

data class ScreeningWithMovie(
        val movie: Movie,
        val screening: Screening
)
