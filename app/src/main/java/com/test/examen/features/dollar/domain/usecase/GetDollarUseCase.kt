package com.test.examen.features.dollar.domain.usecase

import com.test.examen.features.dollar.domain.model.DollarModel
import com.test.examen.features.dollar.domain.repository.IDollarRepository
import kotlinx.coroutines.flow.Flow

class GetDollarUseCase(
    val repository: IDollarRepository
) {

    suspend fun invoke(): Flow<DollarModel> {
        return repository.getDollar()
    }
}