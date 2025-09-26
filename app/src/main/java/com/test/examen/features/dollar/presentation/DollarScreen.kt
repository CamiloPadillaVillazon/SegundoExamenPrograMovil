package com.test.examen.features.dollar.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@Composable
fun DollarScreen(
    viewModelDollar: DollarViewModel = koinViewModel()
) {
    val state by viewModelDollar.uiState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (val s = state) {
            is DollarViewModel.DollarUIState.Error -> {
                Text(
                    text = s.message,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
            }
            DollarViewModel.DollarUIState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.size(48.dp))
            }
            is DollarViewModel.DollarUIState.Success -> {
                val m = s.data
                GridRow(
                    leftTitle = "Oficial",
                    leftValue = m.dollarOfficial.orEmpty(),
                    rightTitle = "Paralelo",
                    rightValue = m.dollarParallel.orEmpty()
                )
                Spacer(modifier = Modifier.height(12.dp))
                GridRow(
                    leftTitle = "USDT",
                    leftValue = m.usdt.orEmpty(),
                    rightTitle = "USDC",
                    rightValue = m.usdc.orEmpty()
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Última actualización (La Paz)",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    ),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = formatTsLocal(m.updatedAt),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun GridRow(
    leftTitle: String,
    leftValue: String,
    rightTitle: String,
    rightValue: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        RateCard(
            title = leftTitle,
            value = leftValue,
            modifier = Modifier.weight(1f)
        )
        RateCard(
            title = rightTitle,
            value = rightValue,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun RateCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = title, style = MaterialTheme.typography.titleMedium, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = value, style = MaterialTheme.typography.headlineSmall, textAlign = TextAlign.Center)
        }
    }
}

private fun formatTsLocal(tsRaw: Long): String {
    if (tsRaw <= 0L) return "—"
    val tsMs = if (tsRaw < 1_000_000_000_000L) tsRaw * 1000 else tsRaw
    val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    sdf.timeZone = TimeZone.getTimeZone("America/La_Paz")
    return sdf.format(Date(tsMs))
}
