package com.test.examen.features.dollar.domain.model

data class DollarModel(
    var dollarOfficial: String? = null,
    var dollarParallel: String? = null,
    var usdt: String? = null,
    var usdc: String? = null,
    var updatedAt: Long = 0
)