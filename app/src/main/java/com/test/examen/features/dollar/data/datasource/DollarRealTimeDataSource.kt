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

    suspend fun getDollarUpdates(): Flow<DollarModel> = callbackFlow {
        val callback = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                val official = snapshot.child("dollarOfficial").value
                val parallel = snapshot.child("dollarParallel").value
                val model = DollarModel(
                    dollarOfficial = official?.toString(),
                    dollarParallel = parallel?.toString()
                )
                trySend(model)
            }
        }
        val ref = Firebase.database.getReference("dollar")
        ref.addValueEventListener(callback)
        awaitClose { ref.removeEventListener(callback) }
    }
}
