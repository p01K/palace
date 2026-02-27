package com.pk.palace.mapper

import com.pk.palace.dto.WorkoutDTO
import com.pk.palace.model.Workout
import com.pk.palace.model.WorkoutId

fun WorkoutDTO.toDomain(): Workout {
    return Workout(
        id = WorkoutId(this.id),
        title = this.title,
        exercises = this.exercises.map { it.toDomain() }
    )
}