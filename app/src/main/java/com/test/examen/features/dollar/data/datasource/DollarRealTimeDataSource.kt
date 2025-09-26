package com.test.examen.features.dollar.data.datasource

import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.test.examen.features.dollar.domain.model.DollarModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class DollarRealTimeDataSource {

    fun getDollarUpdates(): Flow<DollarModel> = callbackFlow {
        val ref = Firebase.database.getReference("dollar")
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                fun readString(key: String): String? {
                    val v = snapshot.child(key).value
                    return when (v) {
                        is Number -> v.toString()
                        is String -> v
                        is Boolean -> v.toString()
                        else -> null
                    }
                }
                fun readLong(key: String): Long {
                    val v = snapshot.child(key).value
                    return when (v) {
                        is Number -> v.toLong()
                        is String -> v.toLongOrNull() ?: 0L
                        else -> 0L
                    }
                }
                val model = DollarModel(
                    dollarOfficial = readString("dollarOfficial"),
                    dollarParallel = readString("dollarParallel"),
                    usdt = readString("usdt"),
                    usdc = readString("usdc"),
                    updatedAt = readLong("updatedAt")
                )
                trySend(model)
            }
            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }
        ref.addValueEventListener(listener)
        awaitClose { ref.removeEventListener(listener) }
    }
}
