package com.test.examen.features.movies.data.api.dto

import com.google.gson.annotations.SerializedName

data class MovieListDto(val results: List<MovieDto>)

data class MovieDto(
    val id: Int,
    val title: String,
    @SerializedName("poster_path") val posterPath: String?
)
