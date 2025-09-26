package com.test.examen.features.dollar.data.mapper

import com.test.examen.features.dollar.data.database.entity.DollarEntity
import com.test.examen.features.dollar.domain.model.DollarModel

fun DollarEntity.toModel(): DollarModel {
    return DollarModel(
        dollarOfficial = dollarOfficial,
        dollarParallel = dollarParallel,
        usdt = usdt,
        usdc = usdc,
        updatedAt = timestamp
    )
}

fun DollarModel.toEntity(): DollarEntity {
    val ts = if (updatedAt != 0L) updatedAt else System.currentTimeMillis()
    return DollarEntity(
        dollarOfficial = dollarOfficial,
        dollarParallel = dollarParallel,
        usdt = usdt,
        usdc = usdc,
        timestamp = ts
    )
}
