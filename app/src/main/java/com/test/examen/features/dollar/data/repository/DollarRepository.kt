package com.test.examen.features.dollar.data.repository

import com.test.examen.features.dollar.data.datasource.DollarLocalDataSource
import com.test.examen.features.dollar.data.datasource.DollarRealTimeDataSource
import com.test.examen.features.dollar.domain.model.DollarModel
import com.test.examen.features.dollar.domain.repository.IDollarRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlin.text.insert

class DollarRepository(
    val realTimeRemoteDataSource: DollarRealTimeDataSource,
    val localDataSource: DollarLocalDataSource
) : IDollarRepository {

    override suspend fun getDollar(): Flow<DollarModel> {
        return realTimeRemoteDataSource
            .getDollarUpdates()
            .distinctUntilChanged()
            .onEach { remote ->
                val last = localDataSource.getLast()
                val changed = last == null ||
                        last.dollarOfficial != remote.dollarOfficial ||
                        last.dollarParallel != remote.dollarParallel ||
                        last.usdt != remote.usdt ||
                        last.usdc != remote.usdc
                if (changed) {
                    val ts = System.currentTimeMillis()
                    localDataSource.insert(remote.copy(updatedAt = ts))
                }
            }
            .map { remote ->
                val last = localDataSource.getLast()
                if (last != null) remote.copy(updatedAt = last.updatedAt) else remote
            }
    }
}