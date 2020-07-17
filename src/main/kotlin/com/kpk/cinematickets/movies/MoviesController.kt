package com.kpk.cinematickets.movies

import com.kpk.cinematickets.movies.models.Movie
import com.kpk.cinematickets.movies.models.Screening
import com.kpk.cinematickets.movies.models.Seat
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/movies")
class MoviesController(val moviesRepository: MoviesRepository) {

    @GetMapping("/all")
    fun getMovies(): List<Movie> {
        return moviesRepository.getMovies()
    }

    @GetMapping("/{movieId}/screenings")
    fun getMoviesScreenings(@PathVariable("movieId") movieId: Long): List<Screening> {
        return moviesRepository.getMovieScreenings(movieId)
    }

    @GetMapping("/screening/{screeningId}/seats")
    fun getScreeningRoomSeats(@PathVariable("screeningId") screeningId: Long): List<Seat> {
        return moviesRepository.getScreeningSeats(screeningId)
    }

}
