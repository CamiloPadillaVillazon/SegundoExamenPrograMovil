package com.test.examen.di

import android.app.Application
import androidx.room.Room
import com.google.gson.Gson
import com.test.examen.features.dollar.data.database.AppRoomDatabase
import com.test.examen.features.dollar.data.datasource.DollarLocalDataSource
import com.test.examen.features.dollar.data.datasource.DollarRealTimeDataSource
import com.test.examen.features.dollar.data.repository.DollarRepository
import com.test.examen.features.dollar.domain.repository.IDollarRepository
import com.test.examen.features.dollar.domain.usecase.GetDollarUseCase
import com.test.examen.features.dollar.presentation.DollarViewModel
import com.test.examen.features.movies.data.api.MovieService
import com.test.examen.features.movies.data.database.AppRoomDataBase
import com.test.examen.features.movies.data.datasource.MovieLocalDataSource
import com.test.examen.features.movies.data.datasource.MovieRemoteDataSource
import com.test.examen.features.movies.data.repository.MovieRepository
import com.test.examen.features.movies.domain.repository.IMovieRepository
import com.test.examen.features.movies.domain.usecase.GetPopularMoviesUseCase
import com.test.examen.features.movies.presentation.MoviesViewModel
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkConstants {
    const val RETROFIT_MOVIE = "RetrofitMovie"
    const val MOVIE_BASE_URL = "https://api.themoviedb.org/3/"
}

val appModule = module {
    single { Gson() }
    single {
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }
    single(named(NetworkConstants.RETROFIT_MOVIE)) {
        Retrofit.Builder()
            .baseUrl(NetworkConstants.MOVIE_BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<MovieService> { get<Retrofit>(named(NetworkConstants.RETROFIT_MOVIE)).create(MovieService::class.java) }
    single { MovieRemoteDataSource(get()) }
    single {
        Room.databaseBuilder(get<Application>(), AppRoomDataBase::class.java, "app.db")
            .fallbackToDestructiveMigration()
            .build()
    }
    single { get<AppRoomDataBase>().movieDao() }
    single { MovieLocalDataSource(get(), get()) }
    single<IMovieRepository> { MovieRepository(get(), get()) }
    factory { GetPopularMoviesUseCase(get()) }
    viewModel { MoviesViewModel(get()) }

    single { AppRoomDatabase.getDatabase(get()) }
    single { get<AppRoomDatabase>().dollarDao() }
    single { DollarRealTimeDataSource() }
    single { DollarLocalDataSource(get()) }
    single<IDollarRepository> { DollarRepository(get(), get()) }
    factory { GetDollarUseCase(get()) }
    viewModel{ DollarViewModel(get()) }
}
