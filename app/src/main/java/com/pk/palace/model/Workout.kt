package com.pk.palace.model

@JvmInline
value class WorkoutId(val value: Int)

data class Workout(val id: WorkoutId, val title: String, val exercises: List<Exercise>)
