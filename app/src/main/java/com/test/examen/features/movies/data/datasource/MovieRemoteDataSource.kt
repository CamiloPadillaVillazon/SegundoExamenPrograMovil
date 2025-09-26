package com.test.examen.features.movies.data.datasource

import com.test.examen.features.movies.data.api.MovieService
import com.test.examen.features.movies.data.api.dto.MovieListDto

class MovieRemoteDataSource(
    private val api: MovieService
) {
    suspend fun getPopular(): Result<MovieListDto> {
        val res = api.popular()
        return if (res.isSuccessful && res.body()!=null)
            Result.success(res.body()!!)
        else
            Result.failure(Exception("TMDB error ${res.code()}"))
    }
}
