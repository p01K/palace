package com.pk.palace.repo

import android.util.Log
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.pk.palace.dto.ExerciseDTO
import com.pk.palace.dto.WorkoutDTO
import com.pk.palace.mapper.toDomain
import com.pk.palace.model.Workout
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

data class WorkoutFirestoreDTO(
    val title: String = "",
    val exercises: List<DocumentReference> = emptyList()  // Raw refs
)

class WorkoutRepositoryFirebase(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) : WorkoutRepository {

    override suspend fun listAll(): List<Workout> {
        val workouts = db.collection("workouts").get().await()
        return workouts.map { toDTO(it) }.map { it.toDomain() }
    }

    private suspend fun toDTO(doc: DocumentSnapshot): WorkoutDTO {
        // Deserialize the workout doc into the raw wrapper (no ExerciseDTO here)
        val raw = doc.toObject(WorkoutFirestoreDTO::class.java)
            ?: return WorkoutDTO()

        // Now fetch each referenced exercise document in parallel
        val exercises: List<ExerciseDTO> = coroutineScope {
            raw.exercises.map { ref ->
                async {
                    runCatching {
                        val snapshot = ref.get().await()
                        snapshot.toObject(ExerciseDTO::class.java) ?: run {
                            Log.w("WorkoutRepo", "Failed to deserialize exercise: ${ref.path}")
                            null
                        }
                    }.onFailure {
                        Log.e("WorkoutRepo", "Error fetching exercise ${ref.path}", it)
                    }.getOrNull()
                }
            }.awaitAll().filterNotNull()
        }

        return WorkoutDTO(
            id = doc.id.toIntOrNull() ?: 0,
            title = raw.title,
            exercises = exercises
        )
    }

    override fun get(id: Int): Flow<Workout?> = callbackFlow {
        Log.i("PalaceFirestore", "Trying to fetch workouts with id $id")
        val listener = db.collection("workouts")
            .document(id.toString())
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    trySend(null)
                    return@addSnapshotListener
                }

//                val bite = snapshot?.toObject(WorkoutFirestoreDTO::class.java)?.toDomain()
                trySend(null)
            }

        // Clean up when flow is closed
        awaitClose { listener.remove() }
    }
}
