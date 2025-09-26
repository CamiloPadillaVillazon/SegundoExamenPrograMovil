package com.test.examen.features.movies.domain.usecase

import com.test.examen.features.movies.domain.model.MovieModel
import com.test.examen.features.movies.domain.repository.IMovieRepository

class GetPopularMoviesUseCase(
    private val movieRepository: IMovieRepository
) {
    suspend operator fun invoke(): Result<List<MovieModel>> {
        return movieRepository.getPopularMovies()
    }
}