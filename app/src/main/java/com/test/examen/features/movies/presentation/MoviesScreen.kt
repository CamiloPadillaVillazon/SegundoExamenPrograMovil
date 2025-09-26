package com.test.examen.features.movies.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel

@Composable
fun MoviesScreen(
    moviesViewModel: MoviesViewModel = koinViewModel()
) {
    val state by moviesViewModel.state.collectAsState()

    LaunchedEffect(Unit) { moviesViewModel.fetchPopularMovies() }

    when (val s = state) {
        is MoviesViewModel.UiState.Loading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(color = Color(0xFFD32F2F))
            }
        }
        is MoviesViewModel.UiState.Error -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = s.message,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFFB71C1C)
                )
                Spacer(Modifier.height(12.dp))
                Button(
                    onClick = { moviesViewModel.retry() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFD32F2F),
                        contentColor = Color.White
                    )
                ) {
                    Text("Reintentar")
                }
            }
        }
        is MoviesViewModel.UiState.Success -> {
            val list = s.movies
            if (list.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Sin resultados",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color(0xFFB71C1C)
                    )
                }
            } else {
                PopularMoviesScreen(movies = list)
            }
        }
        else -> {}
    }
}
