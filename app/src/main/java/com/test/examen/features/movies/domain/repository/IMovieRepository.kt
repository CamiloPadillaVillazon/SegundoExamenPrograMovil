package com.test.examen.features.movies.domain.repository

import com.test.examen.features.movies.domain.model.MovieModel

interface IMovieRepository {
    suspend fun getPopularMovies(): Result<List<MovieModel>>
}
