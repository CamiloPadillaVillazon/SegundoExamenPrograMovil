package com.test.examen.features.movies.data.api

import com.test.examen.features.movies.data.api.dto.MovieListDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {
    @GET("discover/movie")
    suspend fun popular(
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("api_key") apiKey: String = "fa3e844ce31744388e07fa47c7c5d8c3"
    ): Response<MovieListDto>
}
