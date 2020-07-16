package com.kpk.cinematickets.movies

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController("movies")
class MoviesController {

    @GetMapping("/all")
    fun getMovies(): List<String> {
        return listOf("The change up", "Django")
    }

}
