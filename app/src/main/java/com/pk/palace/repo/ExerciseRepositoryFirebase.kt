package com.pk.palace.repo

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.pk.palace.dto.ExerciseDTO
import com.pk.palace.mapper.toDomain
import com.pk.palace.model.Exercise
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class ExerciseRepositoryFirebase(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) : ExerciseRepository {

    override suspend fun listAll(): List<Exercise> {
        return db.collection("notes").get().await()
            .toObjects(ExerciseDTO::class.java)
            .map{ it.toDomain() };
    }

    override fun get(id: Int): Flow<Exercise?> = callbackFlow {
        Log.i("PalaceFirestore", "Trying to fetch note with id $id")
        val listener = db.collection("notes")
            .document(id.toString())
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    trySend(null)
                    return@addSnapshotListener
                }

                val bite = snapshot?.toObject(ExerciseDTO::class.java)?.toDomain()
                trySend(bite)
            }

        // Clean up when flow is closed
        awaitClose { listener.remove() }
    }
}