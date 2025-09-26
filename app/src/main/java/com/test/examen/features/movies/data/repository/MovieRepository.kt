package com.test.examen.features.movies.data.repository

import com.test.examen.features.movies.data.datasource.MovieLocalDataSource
import com.test.examen.features.movies.data.datasource.MovieRemoteDataSource
import com.test.examen.features.movies.domain.model.MovieModel
import com.test.examen.features.movies.domain.repository.IMovieRepository

class MovieRepository(
    private val ds: MovieRemoteDataSource,
    private val local: MovieLocalDataSource
) : IMovieRepository {
    override suspend fun getPopularMovies(): Result<List<MovieModel>> {
        return ds.getPopular().map { dto ->
            val list = dto.results.map { MovieModel(id = it.id, title = it.title, posterPath = it.posterPath) }
            local.replaceAll(list)
            list
        }
    }
}
