package com.pk.palace.repo

import com.pk.palace.model.Exercise
import kotlinx.coroutines.flow.Flow

interface ExerciseRepository {
    suspend fun listAll(): List<Exercise>

    fun get(id: Int): Flow<Exercise?>
}