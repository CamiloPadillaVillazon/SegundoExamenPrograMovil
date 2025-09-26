package com.test.examen.features.movies.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.test.examen.features.movies.data.database.dao.IMovieDao
import com.test.examen.features.movies.data.database.entity.MovieEntity

@Database(
    entities = [MovieEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppRoomDataBase : RoomDatabase() {
    abstract fun movieDao(): IMovieDao
}