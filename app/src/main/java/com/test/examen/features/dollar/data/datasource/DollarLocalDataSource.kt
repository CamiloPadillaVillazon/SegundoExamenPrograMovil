package com.test.examen.features.dollar.data.datasource

import com.test.examen.features.dollar.data.database.dao.IDollarDao
import com.test.examen.features.dollar.data.mapper.toEntity
import com.test.examen.features.dollar.data.mapper.toModel
import com.test.examen.features.dollar.domain.model.DollarModel

class DollarLocalDataSource(
    val dao: IDollarDao
) {

    suspend fun getList(): List<DollarModel> {
        return dao.getList().map {
            it.toModel()
        }

    }
    suspend fun deleteAll() {
        dao.deleteAll()
    }
    suspend fun inserTDollars(list: List<DollarModel>) {
        val dollarEntity = list.map { it.toEntity() }
        dao.insertDollars(dollarEntity)
    }

    suspend fun insert(dollar: DollarModel) {
        dao.insert(dollar.toEntity())
    }

}