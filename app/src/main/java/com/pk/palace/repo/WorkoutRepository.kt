package com.pk.palace.repo

import com.pk.palace.model.Workout
import kotlinx.coroutines.flow.Flow

interface WorkoutRepository {
    suspend fun listAll(): List<Workout>

    fun get(id: Int): Flow<Workout?>
}