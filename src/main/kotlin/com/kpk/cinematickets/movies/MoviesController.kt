package com.kpk.cinematickets.movies

import com.kpk.cinematickets.movies.models.Movie
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/movies")
class MoviesController(val moviesRepository: MoviesRepository) {

    @GetMapping("/all")
    fun getMovies(): List<Movie> {
        return moviesRepository.getMovies()
    }

}
