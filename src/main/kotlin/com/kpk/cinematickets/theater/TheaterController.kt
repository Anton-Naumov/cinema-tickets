package com.kpk.cinematickets.theater

import com.kpk.cinematickets.theater.models.Movie
import com.kpk.cinematickets.theater.models.Screening
import com.kpk.cinematickets.theater.models.Seat
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/movies")
class TheaterController(val theaterRepository: TheaterRepository) {

    @GetMapping("/all")
    fun getMovies(): List<Movie> {
        return theaterRepository.getMovies()
    }

    @GetMapping("/{movieId}/screenings")
    fun getMoviesScreenings(@PathVariable("movieId") movieId: Long): List<Screening> {
        return theaterRepository.getMovieScreenings(movieId)
    }

    @GetMapping("/screening/{screeningId}/seats")
    fun getScreeningRoomSeats(@PathVariable("screeningId") screeningId: Long): List<Seat> {
        return theaterRepository.getScreeningSeats(screeningId)
    }

}
