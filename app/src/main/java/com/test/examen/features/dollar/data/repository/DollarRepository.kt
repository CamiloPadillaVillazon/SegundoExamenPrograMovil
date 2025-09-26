package com.test.examen.features.dollar.data.repository

import com.test.examen.features.dollar.data.datasource.DollarLocalDataSource
import com.test.examen.features.dollar.data.datasource.DollarRealTimeDataSource
import com.test.examen.features.dollar.domain.model.DollarModel
import com.test.examen.features.dollar.domain.repository.IDollarRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

class DollarRepository(
    val realTimeRemoteDataSource: DollarRealTimeDataSource,
    val localDataSource: DollarLocalDataSource
): IDollarRepository {

    override suspend fun getDollar(): Flow<DollarModel> {
        return realTimeRemoteDataSource.getDollarUpdates()
            .onEach {
                localDataSource.insert(it)
            }

    }
}