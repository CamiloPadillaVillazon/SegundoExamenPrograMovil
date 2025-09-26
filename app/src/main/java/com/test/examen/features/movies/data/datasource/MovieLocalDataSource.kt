package com.test.examen.features.movies.data.datasource

import com.google.gson.Gson
import com.test.examen.features.movies.data.database.dao.IMovieDao
import com.test.examen.features.movies.data.database.entity.MovieEntity
import com.test.examen.features.movies.domain.model.MovieModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieLocalDataSource(
    private val dao: IMovieDao,
    private val gson: Gson
) {
    fun observeAll(): Flow<List<MovieModel>> {
        return dao.observeAll().map { list ->
            list.map { gson.fromJson(it.json, MovieModel::class.java) }
        }
    }

    suspend fun replaceAll(items: List<MovieModel>) {
        val entities = items.map { MovieEntity(id = it.id, json = gson.toJson(it)) }
        dao.clearAll()
        dao.upsertAll(entities)
    }

    suspend fun getAll(): List<MovieModel> {
        return dao.getAll().map { gson.fromJson(it.json, MovieModel::class.java) }
    }
}